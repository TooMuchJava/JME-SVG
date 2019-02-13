/*
 * The MIT License
 *
 * Copyright 2019 Indigo Amann.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.lazydev.jme.svg;

import com.jme3.asset.AssetProcessor;
import com.jme3.asset.TextureKey;
import com.jme3.texture.TextureProcessor;

public class SVGTextureKey extends TextureKey {
    public int width, hight;
    /**
     * @param name "Path/to/image.svg"
     * @param width Desired width
     * @param height Desired height
     */
    public SVGTextureKey(String name, int width, int height) {
        super(name);
        this.width = width;
        this.hight = height;
    }
    @Override
    public Class<? extends AssetProcessor> getProcessorType(){
        return TextureProcessor.class;
    }
}
