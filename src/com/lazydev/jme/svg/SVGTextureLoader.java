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

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import com.jme3.texture.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SVGTextureLoader implements AssetLoader {
    @Override
    public Image load(AssetInfo assetInfo) throws IOException {
        InputStream stream = assetInfo.openStream();
        try {
            if (!(assetInfo.getKey() instanceof SVGTextureKey)) throw new IllegalArgumentException("SVGTextureLoader requies a SVGTextureKey as the AssetInfo key.");
            SVGTextureKey texKey = (SVGTextureKey) assetInfo.getKey();
            return SVGUtil.instance().createJMEImage(stream, texKey.width, texKey.hight, texKey);
            //return new Texture2D(image);
        } catch (Exception ex) {
            Logger.getLogger(SVGTextureLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}