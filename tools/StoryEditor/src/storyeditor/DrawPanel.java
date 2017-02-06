/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package storyeditor;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author mgohde
 */
public class DrawPanel extends JPanel
{
    private BufferedImage img;
    public DrawPanel()
    {
        this.img=null;
    }
    
    public BufferedImage getImg()
    {
        return this.img;
    }
    
    public void setImg(BufferedImage img)
    {
        this.img=img;
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        System.out.println("Drawing stuff.");
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }
}
