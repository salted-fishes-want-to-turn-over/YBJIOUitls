import org.junit.Test;

import java.io.*;

public class IO_Practice {

    /********************************************************  字节流   ***********************************************/
    /***
     * 基于内存的字节输入输出流
     */
    @Test
    public void FileStreamTest(){
        File file = new File("src/main/resources/study.jpg");
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        /***
         * 如果流的打开放在try()中，则不需要显示的去关闭
         * 如果用了缓存，只有缓存满了和关闭流会触发缓存的刷盘操作； 所以调用flush进行强制刷盘操作
         */
        try {
            inputStream = new FileInputStream(file);
            outputStream = new FileOutputStream(new File("src/main/resources/study_copy.jpg"));
            int len = 0;
            byte[] buffer = new byte[1024]; // 利用缓存技术
            while ((len = inputStream.read(buffer)) != -1){
//                (len = inputStream.read()) != -1
//                outputStream.write(len);  // 需要与磁盘交互1000次，
                outputStream.write(buffer, 0, len); // 事先写到缓存中，可以减少IO写入是与磁盘的交互次数
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * 基于内存的字节输入输出流
     */
    @Test
    public void ByteArrayStreamTest(){
        String str = "hello, byte array";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = 0;
        while ((len = byteArrayInputStream.read()) != -1){
            outputStream.write(Character.toUpperCase((char)len));
        }
        System.out.println(outputStream.toString());
    }

    /***
     * 基于缓存流的输入输出
     *  和没用缓存的对比
     */
    @Test
    public void compareToBuffer()
    {
        long start = System.currentTimeMillis();
        NormalCopyDemo();
        long end = System.currentTimeMillis();
        System.out.println("normal copy demo consume time: " + (end - start));
        start = System.currentTimeMillis();
        BufferCopyDemo();
        end = System.currentTimeMillis();
        System.out.println("normal copy demo consume time: " + (end - start));
    }


    public static void NormalCopyDemo(){
        File fileSource = new File("src/main/resources/dog.mp4");
        File fileTarget = new File("src/main/resources/dog_copy.mp4");
        try(FileInputStream inputStream = new FileInputStream(fileSource);   // 流开启放在try方法中，可以不显式的关闭
            FileOutputStream fileOutputStream = new FileOutputStream(fileTarget)){
            int len = 0;
            byte[] buffer = new byte[128];
            while ((len = inputStream.read(buffer)) != -1){
                fileOutputStream.write(buffer, 0, len);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /***
     * bufferStream默认是有8k的缓存
     */
    public static void BufferCopyDemo(){
        File fileSource = new File("src/main/resources/dog.mp4");
        File fileTarget = new File("src/main/resources/dog_copy.mp4");
        try(FileInputStream inputStream = new FileInputStream(fileSource);   // 流开启放在try方法中，可以不显式的关闭
            FileOutputStream fileOutputStream = new FileOutputStream(fileTarget);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)){
            int len = 0;
            while ((len = bufferedInputStream.read()) != -1){
                bufferedOutputStream.write(len);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    /********************************************************  字符流   ***********************************************/
    @Test
    public void StreamConvertReaderWrite(){
        try(BufferedInputStream bufferedInputStream = new BufferedInputStream(
                new FileInputStream(
                        new File("src/main/resources/input.txt")));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                    new FileOutputStream(
                            new File("src/main/resources/out.txt")))) {

            BufferedReader bufferedReader = new BufferedReader(  // 字节流到字符流的桥梁
                    new InputStreamReader(bufferedInputStream, "utf-8"));
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(bufferedOutputStream, "utf-8"));

            String res = bufferedReader.readLine();
            System.out.println(res);

            bufferedWriter.write("你好啊，bufferWriter");
            bufferedWriter.flush();  // 利用缓存时，需要刷盘动作


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
