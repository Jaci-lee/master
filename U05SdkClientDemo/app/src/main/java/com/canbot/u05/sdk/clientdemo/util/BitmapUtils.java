/*
 *   Copyright (C)  2016 android@19code.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.canbot.u05.sdk.clientdemo.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.YuvImage;
import android.media.ThumbnailUtils;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Create by h4de5ing 2016/5/21 021.
 * https://github.com/sharinghuang/ASRabbit
 * unchecked
 */
public class BitmapUtils {
        private static final int THUMB_WIDTH = 200;

        private static final int THUMB_HEIGHT = 200;

        private static final String IMAGE_FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BitmapTemp/";

        public static Bitmap decodeFile(String pathName, int width, int height) {
                Bitmap bitmap = null;
                if (FileUtils.isFileExists(pathName)) {
                        BitmapFactory.Options opts = new BitmapFactory.Options();
                        opts.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(pathName, opts);
                        opts.inSampleSize = ImageUtils.calculateInSampleSize(opts, width, height);
                        opts.inJustDecodeBounds = false;
                        opts.inPurgeable = true;
                        opts.inInputShareable = true;
                        opts.inPreferredConfig = Bitmap.Config.ALPHA_8;
                        opts.inDither = true;
                        bitmap = BitmapFactory.decodeFile(pathName, opts);
                        bitmap = BitmapFactory.decodeFile(pathName, opts);

                        bitmap = BitmapFactory.decodeFile(pathName, opts);
                        System.gc();
                }
                if (bitmap == null) {
                        bitmap = getImageThumbnail(pathName);
                }
                return bitmap;
        }

        public static Bitmap getImageThumbnail(String imagePath) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
                options.inJustDecodeBounds = false;
                int h = options.outHeight;
                int w = options.outWidth;
                int beWidth = w / THUMB_WIDTH;
                int beHeight = h / THUMB_HEIGHT;
                int be = 4;
                if (beWidth < beHeight && beHeight >= 1) {
                        be = beHeight;
                }
                if (beHeight < beWidth && beWidth >= 1) {
                        be = beWidth;
                }
                if (be <= 0) {
                        be = 1;
                } else if (be > 3) {
                        be = 3;
                }
                options.inSampleSize = be;
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                options.inPurgeable = true;
                options.inInputShareable = true;
                try {
                        bitmap = BitmapFactory.decodeFile(imagePath, options);
                        bitmap = ThumbnailUtils.extractThumbnail(bitmap, THUMB_WIDTH, THUMB_HEIGHT, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
                } catch (OutOfMemoryError e) {
                        System.gc();
                }
                if (bitmap == null) {
                        bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ALPHA_8);
                }
                return bitmap;
        }

        public static Bitmap createCircularClip(Bitmap input, int width, int height) {
                if (input == null) {
                        return null;
                }

                final int inWidth = input.getWidth();
                final int inHeight = input.getHeight();
                final Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                final Canvas canvas = new Canvas(output);
                final Paint paint = new Paint();
                paint.setShader(new BitmapShader(input, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                paint.setAntiAlias(true);
                final RectF srcRect = new RectF(0, 0, inWidth, inHeight);
                final RectF dstRect = new RectF(0, 0, width, height);
                final Matrix m = new Matrix();
                m.setRectToRect(srcRect, dstRect, Matrix.ScaleToFit.CENTER);
                canvas.setMatrix(m);
                canvas.drawCircle(inWidth / 2, inHeight / 2, inWidth / 2, paint);
                return output;
        }


        /**
         * @param bitmap  ԴͼƬ
         * @param width   ���ź��
         * @param height  ���ź��
         * @param quality ��������
         * @return
         */
        public static byte[] transImage(Bitmap bitmap, int width, int height,
                                        int quality) {

                try {
                        int bitmapWidth = bitmap.getWidth();
                        int bitmapHeight = bitmap.getHeight();
                        // ����ͼƬ�ĳߴ�
                        float scaleWidth = (float) width / bitmapWidth;
                        float scaleHeight = (float) height / bitmapHeight;
                        Matrix matrix = new Matrix();
                        matrix.postScale(scaleWidth, scaleHeight);
                        // �������ź��Bitmap����
                        Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                                bitmapWidth, bitmapHeight, matrix, false);
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        resizeBitmap.compress(CompressFormat.JPEG, quality, outputStream);
                        byte[] byteArray = outputStream.toByteArray();
                        outputStream.close();
                        if (!bitmap.isRecycled()) {
                                bitmap.recycle();
                        }
                        if (!resizeBitmap.isRecycled()) {
                                resizeBitmap.recycle();
                        }
                        return byteArray;
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return null;
        }

        public static void saveAsFile(Bitmap bmp, String fileName, String path) {

                File fileDir = new File(path);
                if (!fileDir.exists()) {
                        fileDir.mkdirs();
                }
                try {
                        File file = new File(fileDir,
                                fileName);
                        FileOutputStream outputStream = new FileOutputStream(file);
                        bmp.compress(CompressFormat.JPEG, 100, outputStream);
                        outputStream.close();
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        public static Bitmap byteToBitmap(byte[] data, int width, int height) {

                YuvImage image = new YuvImage(data, ImageFormat.NV21,
                        width, height, null);
                if (image != null) {
                        // ����Դ����
                        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                        image.compressToJpeg(
                                new Rect(0, 0, width, height), 100,
                                outstream);
                        Bitmap src = BitmapFactory.decodeByteArray(outstream.toByteArray(), 0, outstream.size());
                        return src;
                }
                return null;

        }

        public static void saveByteAsFile(byte[] data, String path, String fileName, int width, int height) {

                try {
                        File dir = new File(path);
                        if (!dir.exists()) {
                                dir.mkdirs();
                        }
                        YuvImage image = new YuvImage(data, ImageFormat.NV21,
                                width, height, null);
                        if (image != null) {
                                // ����Դ����
                                ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                                image.compressToJpeg(
                                        new Rect(0, 0, width, height), 100,
                                        outstream);
                                byte[] srcData = outstream.toByteArray();
                                int len = srcData.length;
                                Bitmap src = BitmapFactory.decodeByteArray(srcData, 0, len);
                                File file = new File(path,
                                        fileName);

                                FileOutputStream outputStream = new FileOutputStream(file);
                                src.compress(CompressFormat.JPEG, 100, outputStream);
                                outputStream.close();
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        /**
         * ����ͼ��Ƕ�
         *
         * @param bm                ԴͼƬ
         * @param orientationDegree ��ת�Ƕ�
         * @return
         */
        public static Bitmap adjustPhotoRotation(Bitmap bm, int orientationDegree) {
                Matrix m = new Matrix();
                m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
                Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
                return bm1;
        }
}
