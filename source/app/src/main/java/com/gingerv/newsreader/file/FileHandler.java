package com.gingerv.newsreader.file;

import android.content.Context;

import com.gingerv.newsreader.database.News;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileHandler {
    public static boolean writeNewsIntoLocal(Context context, String fileName, News news) {
        try {
            // filenames can not have slashes, file extension is set to .nr
            FileOutputStream fileOutputStream = context.openFileOutput(fileName + ".nr", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(news);
            fileOutputStream.close();
            objectOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static News readNewsFromLocal(Context context, String fileName) {
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName + ".nr");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            News news = (News) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
            return news;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean deleteNewsFromLocal(Context context, String fileName) {
        try {
            context.deleteFile(fileName + ".nr");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
