package com.vuforia.samples.SampleApplication.utils;


import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

/**
 * Created by admin on 2018/3/2.
 */

public class Model extends MeshObject {
    
    private Buffer mVertBuff;//顶点
    private Buffer mNormBuff;//normal
    private Buffer mTexCoordBuff;//纹理坐标
    private int verticesNumber = 0;
    
    public void loadModel (AssetManager assetManager, String fileName)
        throws IOException
    {
        InputStream inputFile = null;
        try{
            inputFile = assetManager.open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputFile));
            
            // 获取verts的数量
            String line = reader.readLine();
            int floatsToRead = Integer.parseInt(line);
            verticesNumber = floatsToRead;
            
            // 设置vertex
            double[] model_VERTS = new double[3*floatsToRead];
            for(int i=0;i<floatsToRead;i++){
                String curline = reader.readLine();
                if(curline.indexOf('/')>=0){
                    i--;
                    continue;
                }
                // 将一行分成3个数据
                String floatStrs[] = curline.split(",");
                model_VERTS[3*i] = Float.parseFloat(floatStrs[0]);
                model_VERTS[3*i+1] = Float.parseFloat(floatStrs[1]);
                model_VERTS[3*i+2] = Float.parseFloat(floatStrs[2]);
            }
            mVertBuff = fillBuffer(model_VERTS);
            
            // 设置normal
            double[] model_NORMS = new double[3*floatsToRead];
            for (int i = 0; i < floatsToRead; i++)
            {
                String curline = reader.readLine();
                if( curline.indexOf('/') >= 0 ){
                    i--;
                    continue;
                }
                //将一行分成三个数据
                String floatStrs[] = curline.split(",");
                model_NORMS[3*i] = Float.parseFloat(floatStrs[0]);
                model_NORMS[3*i+1] = Float.parseFloat(floatStrs[1]);
                model_NORMS[3*i+2] = Float.parseFloat(floatStrs[2]);
            }
            mNormBuff = fillBuffer(model_NORMS);
            
            // 设置texcoord
            double[] model_TEX_COORDS = new double[2*floatsToRead];
            for (int i = 0; i < floatsToRead; i++)
            {
                String curline = reader.readLine();
                if( curline.indexOf('/') >= 0 ){
                    i--;
                    continue;
                }
                //将一行分成两个数据
                String floatStrs[] = curline.split(",");
                model_TEX_COORDS[2*i] = Float.parseFloat(floatStrs[0]);
                model_TEX_COORDS[2*i+1] = Float.parseFloat(floatStrs[1]);
            }
            mTexCoordBuff = fillBuffer(model_TEX_COORDS);
        } finally {
            if(inputFile != null){
                inputFile.close();
            }
        }
    }
    
    @Override
    public int getNumObjectVertex() {
        return verticesNumber;
    }
    
    @Override
    public int getNumObjectIndex() {
        return 0;
    }
    
    @Override
    public Buffer getBuffer(BUFFER_TYPE bufferType) {
        Buffer result = null;
        switch (bufferType)
        {
            case BUFFER_TYPE_VERTEX:
                result = mVertBuff;
                break;
            case BUFFER_TYPE_TEXTURE_COORD:
                result = mTexCoordBuff;
                break;
            case BUFFER_TYPE_NORMALS:
                result = mNormBuff;
                break;
            default:
                break;
        }
        return result;
    }
}
