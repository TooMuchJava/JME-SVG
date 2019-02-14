/*
 * Copyright 2019 Indigo Amann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lazydev.jme.svg;

import com.jme3.asset.AssetManager;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import java.io.InputStream;
/**
 * Interface for the utility class. Extended for java.awt & android support.
 */
public interface SVGUtil {
    /**
     * Creates a JME Image from an input stream with a SVG document.
     * @param stream The stream with the SVG document
     * @param width The output image width
     * @param height The output image height
     * @param key <b>(CAN BE NULL)</b> The AssetManager key
     * @return A <code>com.jme3.texture.Image</code> of the SVG document that is the width and height you requested.
     * @throws java.lang.Exception 
     */
    public Image createJMEImage(InputStream stream, int width, int height, SVGTextureKey key) throws Exception;
    /**
     * Like calling Lemur's GuiGlobals.loadTexture!
     * @param assets Your AssetManager instance
     * @param path Path to your image, EX: "Textures/example.svg"
     * @param width Output texture width
     * @param height Output texture height
     * @param repeat Honestly, I'm not completely sure... I copied that from Lemur...
     * @return 
     */
    public static Texture loadSVGTexture(AssetManager assets, String path, int width, int height) {
        SVGTextureKey key = new SVGTextureKey(path, width, height);
        Texture t = assets.loadAsset(key);
        if( t == null ) {
            throw new RuntimeException("Error loading SVG texture:" + path);
        }
        return t;
    }
    /**
     * For some reason you cant do prifate static non-final variables in an interface... The solution: InstanceHolder.
     */
    static class InstanceHolder {
        private static SVGUtil instance;
    }
    /**
     * Gets the instance. If one isn't generated, it creates one, either AWT_SVGUtil or Android_SVGUtil, depending upon if you are using an android runtime.
     * @return The utility instance
     */
    public static SVGUtil instance() {
        if (InstanceHolder.instance == null) {
            String name = System.getProperty("java.runtime.name").toLowerCase();
            if ("android runtime".equals(name)) {
                InstanceHolder.instance = new Android_SVGUtil();
            } else {
                InstanceHolder.instance = new AWT_SVGUtil();
            }
        }
        return InstanceHolder.instance;
    }
    /**
     * Call this so you can use AssetManager.loadAsset to load SVGs.
     * The syntax of that is AssetManager.loadAsset(new SVGTextureKey([path], [width], [height]))
     * @param manager 
     */
    public static void addSVGSupportToAssetManager(AssetManager manager) {
        manager.registerLoader(SVGTextureLoader.class, "svg");
    }
}
