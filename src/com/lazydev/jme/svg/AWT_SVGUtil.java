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

import com.jme3.texture.Image;
import com.jme3.texture.plugins.AWTLoader;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

public class AWT_SVGUtil implements SVGUtil {
    protected AWTLoader loader;
    int svgn = 0;
    public AWT_SVGUtil() {
        loader = new AWTLoader();
    }
    public BufferedImage createBufferedImage(InputStream stream, int width, int height) throws IOException, TranscoderException {
        PNGTranscoder t = new PNGTranscoder();
        t.addTranscodingHint(PNGTranscoder.KEY_WIDTH, new Float(width));
        t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, new Float(height));
        TranscoderInput input = new TranscoderInput(stream);
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        TranscoderOutput output = new TranscoderOutput(ostream);
        t.transcode(input, output);
        ByteArrayInputStream instream = new ByteArrayInputStream(ostream.toByteArray());
        
        return ImageIO.read(instream);
    }
    public Image createJMEImage(BufferedImage image) {
        //ByteBuffer buf = ByteBuffer.allocateDirect(image.getHeight() * image.getWidth() * 4);
        //ImageToAwt.convert(image, Image.Format.ABGR8, buf);
        return loader.load(image, true);//new Image(Image.Format.ABGR8, 640, 480, buf);
    }
    @Override
    public Image createJMEImage(InputStream stream, int width, int height, SVGTextureKey key) throws IOException, TranscoderException {
        return createJMEImage(createBufferedImage(stream, width, height));
    }
}
