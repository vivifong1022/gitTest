import java.awt.Color;

public class Test extends Picture {
    
  public Test()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Test(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param width the width of the desired picture
   * @param height the height of the desired picture
   */
  public Test(int width, int height)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   */
  public Test(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  

    

    public void removeRedEye(int startX, int startY, int endX, int endY, Color newColor){
      Pixel pixelObj= null;
      for (int x = startX; x < endX; x++){
        for (int y = startY; y < endY; y++){// get the current pixel
          pixelObj= getPixel(x,y);
           // if the color is near red then change it
           if (pixelObj.colorDistance(Color.red) < 176){
            pixelObj.setColor(newColor);
          }
        }
      }
    }
  

    public void edgeDetection(int threshold) {
      int width = this.getWidth();
      int height = this.getHeight();
      
      for (int y = 0; y < height - 1; y++) {
          for (int x = 0; x < width; x++) {
              Pixel currentPixel = this.getPixel(x, y);
              Pixel belowPixel = this.getPixel(x, y + 1);
              
              int currentAvg = (int) currentPixel.getAverage();
              int belowAvg = (int) belowPixel.getAverage();
              
              if (Math.abs(currentAvg - belowAvg) > threshold) {
                  currentPixel.setColor(new Color(0, 0, 0)); // Black for edges
              } else {
                  currentPixel.setColor(new Color(255, 255, 255)); // White for non-edges
              }
          }
      }
  }
  
  
  public void sepiaTone() {
    Pixel[] pixels = this.getPixels();
    for (Pixel p : pixels) {
        int avg = (p.getRed() + p.getGreen() + p.getBlue()) / 3;
        p.setRed(avg);
        p.setGreen(avg);
        p.setBlue(avg);
        
        if (avg < 60) { // Dark shadows, make them darker
            p.setRed((int) (avg * 0.9));
            p.setGreen((int) (avg * 0.9));
            p.setBlue((int) (avg * 0.8));
        } else if (avg < 190) { // Middle grays, reduce blue to make brown
            p.setBlue((int) (avg * 0.7));
        } else { // Light grays, increase red and green for yellow tint
            p.setRed(Math.min(255, (int) (avg * 1.1)));
            p.setGreen(Math.min(255, (int) (avg * 1.05)));
        }
    }
}

  
  public void posterize() {
    Pixel[] pixels = this.getPixels();
    for (Pixel p : pixels) {
        p.setRed(p.getRed() < 64 ? 31 : p.getRed() < 128 ? 95 : p.getRed() < 192 ? 159 : 223);
        p.setGreen(p.getGreen() < 64 ? 31 : p.getGreen() < 128 ? 95 : p.getGreen() < 192 ? 159 : 223);
        p.setBlue(p.getBlue() < 64 ? 31 : p.getBlue() < 128 ? 95 : p.getBlue() < 192 ? 159 : 223);
    }
}



    public static void main(String[] args) {
      try {
        Test r = new Test("imager.png");
        r.show();
        r.removeRedEye(124,111,223,126,Color.BLACK);
        r.repaint();

        Test s = new Test("image.png");
        s.show();
        s.edgeDetection(1);
        s.repaint();

        Test p = new Test("imagep.png");
        p.show();
        p.posterize();
        p.repaint();
        Test q=new Test("images.png");
        q.show();
        q.sepiaTone();
        q.repaint();
      
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
}