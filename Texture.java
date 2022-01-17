
public class Texture {
    public int headerOffset;
    public int w;
    public int h;
    public byte encodingType;
    public int offset;

    
public Texture() {

}

public Texture(int headerOffset) {

    this.headerOffset = headerOffset;

}

public Texture(int w, int h, byte encodingType, int offset) {
    
    this.w =w;
    this.h = h;
    this.encodingType = encodingType;
    this.offset = offset;
}
}
