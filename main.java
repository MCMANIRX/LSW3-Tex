import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Paths;



public class main {

    static File f;
  
    static RandomAccessFile r;

    public static void main (String[] args) throws IOException{

        f = new File(args[0]);

        r = new RandomAccessFile(f, "r");
        int texCnt=0;

        String path = f.getParent();

        if(path == null)
            path = Paths.get("").toAbsolutePath().toString();
          

        
        System.out.printf("Filesize : %d bytes\n",r.readInt());

        r.readInt(); //align
        texCnt = r.readInt();
        System.out.printf("Texture count : %d\n",texCnt);

        Texture[] textures = new Texture[texCnt];

        
        r.seek(r.readInt()); //jmp to beginning of texture list

        for(int i = 0 ; i < texCnt; ++i)
            textures[i] =new Texture(r.readInt());

        int cnt = 0;
        int br = 0;

        byte[] buf;


        String fname  = f.getName().replaceFirst("[.][^.]+$", ""); //filename no extension

        File out_dir = new File(path+"\\"+fname);

        out_dir.mkdir();

        int tc = 0;

        for(Texture tex : textures) {

            r.seek(tex.headerOffset+0x4);
            tex.w = r.readShort();
            tex.h = r.readShort();
            tex.encodingType = (byte)r.readShort();
            r.readShort();
            tex.offset = pos()+r.readInt();

            

            br = (tex.w*tex.h)/2;

            System.out.println("\n\n=====tex"+(tc++)+"=====");
            System.out.printf("Tex offset: %x\n",tex.offset);
            System.out.printf("buf size : %d\nTexture size : %dx%d\n",br,tex.w,tex.h);
            System.out.println("Encoding type: 0x"+Integer.toHexString(tex.encodingType));
            buf = new byte[br];
         
            r.seek(tex.offset);
            r.read(buf);
            TexDec.passBuff(buf);
            TexDec.decCMPR(tex.w, tex.h, path, fname, new String("tex"+cnt++));
        }
        




    
    }

    public static int pos() throws IOException
    {
        return (int) r.getFilePointer();
    }

    public static void p(String txt) {
        System.out.println(txt);
    }
    
}
