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

import com.jme3.asset.AssetProcessor;
import com.jme3.asset.TextureKey;
import com.jme3.asset.cache.AssetCache;
import com.jme3.asset.cache.SimpleAssetCache;
import com.jme3.texture.TextureProcessor;

public class SVGTextureKey extends TextureKey {
    public int width, height;
    /**
     * @param name "Path/to/image.svg"
     * @param width Desired width
     * @param height Desired height
     */
    public SVGTextureKey(String name, int width, int height) {
        super(name);
        this.width = width;
        this.height = height;
    }
    @Override
    public Class<? extends AssetProcessor> getProcessorType(){
        return TextureProcessor.class;
    }
    @Override
    public Class<? extends AssetCache> getCacheType(){
        return SimpleAssetCache.class;
    }
    @Override
    public boolean equals(Object obj) { //Without this, you have scaling issues if you grab the same object twice.
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TextureKey other = (TextureKey) obj;
        if (!super.equals(obj)) {
            return false;
        }
        if (this.isGenerateMips() != other.isGenerateMips()) {
            return false;
        }
        if (this.isFlipY() != other.isFlipY()) {
            return false;
        }
        if (this.getAnisotropy() != other.getAnisotropy()) {
            return false;
        }
        if (this.getTextureTypeHint() != other.getTextureTypeHint()) {
            return false;
        }
        if (this.width != ((SVGTextureKey)other).width || this.height != ((SVGTextureKey)other).height) {
            return false;
        }
        return true;
    }
}
