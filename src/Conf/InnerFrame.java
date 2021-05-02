
package Conf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import javax.swing.Box;
import javax.swing.BoxLayout;


public class InnerFrame extends javax.swing.JInternalFrame 
{
    LinkedList <InternalFrame> frame = new LinkedList();
    ConfluentArticle cluster;

    
    public InnerFrame()
    {
        initComponents();    
    }
    
    public InnerFrame(ConfluentArticle cluster)
    {
        initComponents();
        this.cluster=cluster;
    }
    
    class rsv extends MouseAdapter
    {
        public void mouseEntered(MouseEvent evt)
                        {
                    
                            Delbutton.setVisible(true);
                            
                    
                        }
                        public void mouseExited(MouseEvent evt)
                        {
                          
                            Delbutton.setVisible(false);
                           
                        } 
                        public void mouseClick(MouseEvent evt)
                        {
                         
                            Delbutton.setVisible(true);
                        }
                        public void mousePressed(MouseEvent evt)
                        {
                           
                            Delbutton.setVisible(true);
                        }
                        
                        public void mouseReleased(MouseEvent evt)
                        {
                            
                            Delbutton.setVisible(true);
                        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Delbutton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(390, 600));

        Delbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Conf/del.png"))); // NOI18N
        Delbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DelbuttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 444, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 335, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Delbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Delbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    public void showArticles()
    {
        int i=0;
        LinkedList<FeedArticle> articles=cluster.matchedArticles;
        int n=articles.size();
        for(FeedArticle x:articles)
        {
            InternalFrame added= new InternalFrame(x);
            added.setResizable(true);
            Dimension k = new Dimension();
            k.width = 375;
            k.height = Constants.articleHeight*n;
            jPanel1.setPreferredSize(k);
            jScrollPane1.setPreferredSize(k);
            added.setBounds(0, i*250, 330, 250);
            added.setVisible(true);
            added.getContentPane().setBackground(Color.white);
            jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.PAGE_AXIS));
            //jPanel1.setLayout(new BorderLayout());
            jPanel1.add(added);
            jPanel1.add(Box.createRigidArea(new Dimension(5,0)));
            //added.jLabel1.setText(l2.get(i));
            Delbutton.setVisible(false);
            added.test();
            rsv Handler=new rsv();
            this.addMouseListener(Handler);
            Delbutton.addMouseListener(Handler);
            added.showData();
            frame.add(added);
        }
        
        
    }
    
    private void DelbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DelbuttonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_DelbuttonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton Delbutton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
