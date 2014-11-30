/******************************************************************************
 * Copyright (c) 2012 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 * $Id$
 ******************************************************************************/
//�쐬��: 2013/09/09

package nu.mine.kino.gae;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class Text {
    private static final String MS932 = "MS932";
    private static final String EUCJP = "EUC-JP";
    private static final String UTF8 = "UTF-8";
    private String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setUnicode(String unicode) {
        String source = unicode2Text(unicode);
        setSource(source);
    }

    public String getUnicode() {
        return text2Unicode(source);
    }

    public String getURLEncodeUTF8() {
        return this.getURLEncode(UTF8);
    }

    public String getURLEncodeEUCJP() {
        return this.getURLEncode(EUCJP);
    }

    public String getURLEncodeMS932() {
        return this.getURLEncode(MS932);
    }

    public String getBase64Encode() {
        try {
            System.out.println(source);
            byte[] encodeBase64 =
                Base64.encodeBase64(source.getBytes(UTF8), false);

            return new String(encodeBase64);
        } catch (UnsupportedEncodingException e) {
            // TODO �����������ꂽ catch �u���b�N
            e.printStackTrace();
        }
        return source;
    }

    public String getSHA1() {
        return getHash("SHA1");
    }

    public String getMD5() {
        return getHash("MD5");
    }

    private String getHash(String method) {
        try {
            MessageDigest md = MessageDigest.getInstance(method);
            md.update(source.getBytes(UTF8));
            return toHexString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return source;
    }

    private String toHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        String plainText = null;
        for (int i = 0; i < b.length; i++) {
            plainText = Integer.toHexString(0xFF & b[i]);
            if (plainText.length() < 2) {
                plainText = "0" + plainText;
            }
            hexString.append(plainText);
        }
        return new String(hexString);
    }

    private String unicode2Text(String unicode) {
        return StringEscapeUtils.unescapeJava(unicode);
    }

    private String text2Unicode(String text) {
        return StringEscapeUtils.escapeJava(text);
    }

    private String getURLEncode(String encoding) {
        try {
            return URLEncoder.encode(source, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return source;
    }

    private void setURLEncode(String input, String encoding) {
        try {
            source = URLDecoder.decode(input, encoding);
        } catch (UnsupportedEncodingException e) {
            // TODO �����������ꂽ catch �u���b�N
            e.printStackTrace();
        }
    }

    public void setURLEncodeUTF8(String input) {
        this.setURLEncode(input, UTF8);
    }

    public void setURLEncodeEUCJP(String input) {
        this.setURLEncode(input, EUCJP);
    }

    public void setURLEncodeMS932(String input) {
        this.setURLEncode(input, MS932);
    }

    public void setBase64Encode(String input) {
        try {
            byte[] bytes = input.getBytes(UTF8);
            byte[] decodeBase64 = Base64.decodeBase64(bytes);
            source = new String(decodeBase64, UTF8);
        } catch (UnsupportedEncodingException e) {
            // TODO �����������ꂽ catch �u���b�N
            e.printStackTrace();
        }
    }

    private String getCode(String encoding) {
        try {
            return toHexString(source.getBytes(encoding));
        } catch (UnsupportedEncodingException e) {
            // TODO �����������ꂽ catch �u���b�N
            e.printStackTrace();
        }
        return source;
    }

    public String getUTF8Code() {
        return getCode(UTF8);
    }

    public String getMS932Code() {
        return getCode(MS932);
    }

    public String getEUCJPCode() {
        return getCode(EUCJP);
    }

}