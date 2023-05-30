import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

/** 
 * SaboteurPanel osztály, amely a szabotőrök akcióit kezeli
 */
public class SaboteurPanel extends JPanel {
	
	/**
	 * Az aktuális szabotőr
	 */
    private final Saboteur saboteur;

    public SaboteurPanel(Saboteur saboteur) {
        this.saboteur = saboteur;
        initComponents();
    }

    /**
	 * "Kör vége" gomb
	 */
    private final JButton bEndRound = new JButton();
    
    /**
   	 * "Pumpa átirányítása" gomb
   	 */
    private final JButton bRedirect = new JButton();
    
    /**
	 * "Cső lyukasztása" gomb
	 */
    private final JButton bLeak = new JButton();
    
    /**
	 * "Cső ragadóssá tétele" gomb
	 */
    private final JButton bSticky = new JButton();
    
    /**
	 * "Mozgás" gomb
	 */
    private final JButton bMove = new JButton();
    
    /**
	 * "Cső csúszóssá tétele" gomb
	 */
    private final JButton bSlippery = new JButton();
    
    /**
     * Inicializálja a SaboteurPanel komponenseit
     */
    private void initComponents() {
        JLabel lName = new JLabel();
        JLabel lTeam = new JLabel();


        setBackground(View.PRIMARY_COLOR);
        setForeground(View.SECONDARY_COLOR);
        setPreferredSize(new Dimension(300, 500));

        lName.setFont(new Font("Segoe UI", Font.BOLD, 30)); // NOI18N
        lName.setForeground(View.SECONDARY_COLOR);
        lName.setHorizontalAlignment(SwingConstants.CENTER);
        lName.setText(saboteur.name);

        lTeam.setFont(new Font("Segoe UI", Font.PLAIN, 20)); // NOI18N
        lTeam.setForeground(View.SECONDARY_COLOR);
        lTeam.setHorizontalAlignment(SwingConstants.CENTER);
        lTeam.setText("SZABOTŐR");

        bEndRound.setBackground(View.SECONDARY_COLOR);
        bEndRound.setFont(new Font("Segoe UI", Font.BOLD, 18)); // NOI18N
        bEndRound.setForeground(View.PRIMARY_COLOR);
        bEndRound.setText("KÖR VÉGE");
        bEndRound.addActionListener(this::bEndRoundActionPerformed);

        bRedirect.setBackground(View.SECONDARY_COLOR);
        bRedirect.setFont(new Font("Segoe UI", Font.BOLD, 14)); // NOI18N
        bRedirect.setForeground(View.PRIMARY_COLOR);
        bRedirect.setText("<html><p style=\"text-align:center\">Pumpa<br>átirányítása</p></html>");
        bRedirect.addActionListener(this::bRedirectActionPerformed);

        bLeak.setBackground(View.SECONDARY_COLOR);
        bLeak.setFont(new Font("Segoe UI", Font.BOLD, 14)); // NOI18N
        bLeak.setForeground(View.PRIMARY_COLOR);
        bLeak.setText("Cső lyukasztása");
        bLeak.addActionListener(this::bLeakActionPerformed);

        bSticky.setBackground(View.SECONDARY_COLOR);
        bSticky.setFont(new Font("Segoe UI", Font.BOLD, 14)); // NOI18N
        bSticky.setForeground(View.PRIMARY_COLOR);
        bSticky.setText("<html><p style=\"text-align:center\">Cső ragadóssá<br>tétele</p></html>");
        bSticky.addActionListener(this::bStickyActionPerformed);

        bMove.setBackground(View.SECONDARY_COLOR);
        bMove.setFont(new Font("Segoe UI", Font.BOLD, 14)); // NOI18N
        bMove.setForeground(View.PRIMARY_COLOR);
        bMove.setText("Mozgás");
        bMove.addActionListener(this::bMoveActionPerformed);

        bSlippery.setBackground(View.SECONDARY_COLOR);
        bSlippery.setFont(new Font("Segoe UI", Font.BOLD, 14)); // NOI18N
        bSlippery.setForeground(View.PRIMARY_COLOR);
        bSlippery.setText("<html><p style=\"text-align:center\">Cső csúszóssá<br>tétele</p></html>");
        bSlippery.addActionListener(this::bSlipperyActionPerformed);

        // Generated by NetBeans
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(bEndRound, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lTeam, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lName, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(bMove, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(bRedirect, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(bSticky, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(bSlippery, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addComponent(bLeak, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(lName)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lTeam)
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(bMove, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bRedirect, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(bSticky, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bSlippery, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bLeak, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                                .addComponent(bEndRound, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
        );
        // End of generated code

        refresh();
    }
    
    /**
     * A "Kör vége" gomb megnyomását kezeli
     * 
	 * @param evt a gomb megnyomásakor kiváltódott esemény
     */
    private void bEndRoundActionPerformed(ActionEvent evt) {
        Game.nextPlayer();
        Game.getActivePlayer().drawNameAndButtons(View.GAME_WINDOW);
        View.refresh();
    }

    /**
     * A "Pumpa átirányítása" gomb megnyomását kezeli
     * 
	 * @param evt a gomb megnyomásakor kiváltódott esemény
     */
    private void bRedirectActionPerformed(ActionEvent evt) {
        Object lock = new Object();
        var clickThread = new Thread(() -> {
            Pipe source = null, destination = null;
            var selectorPanel = new SelectorPanel("<html><p style=\"text-align:center\">Válassza<br>ki a bementet!</p></html>", lock);
            View.GAME_WINDOW.setPlayerPanel(selectorPanel);
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException ignored) {
                }
            }
            View.GAME_WINDOW.setPlayerPanel(this);
            if (selectorPanel.selectedComponent != null && selectorPanel.selectedComponent instanceof Pipe pipe)
                source = pipe;
            if (source == null)
                return;
            selectorPanel = new SelectorPanel("<html><p style=\"text-align:center\">Válassza<br>ki a kimenetet!</p></html>", lock);
            View.GAME_WINDOW.setPlayerPanel(selectorPanel);
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException ignored) {
                }
            }
            View.GAME_WINDOW.setPlayerPanel(this);
            if (selectorPanel.selectedComponent != null && selectorPanel.selectedComponent instanceof Pipe pipe)
                destination = pipe;
            if (destination == null)
                return;
            saboteur.redirect(source, destination);
            refresh();
            View.refresh();
        });
        clickThread.start();
    }

    /**
     * A "Cső lyukasztása" gomb megnyomását kezeli
     * 
	 * @param evt a gomb megnyomásakor kiváltódott esemény
     */
    private void bLeakActionPerformed(ActionEvent evt) {
        if (saboteur == null)
            return;
        saboteur.leak();
        refresh();
        View.refresh();
    }

    /**
     * A "Cső ragadóssá tétele" gomb megnyomását kezeli
     * 
	 * @param evt a gomb megnyomásakor kiváltódott esemény
     */
    private void bStickyActionPerformed(ActionEvent evt) {
        if (saboteur == null)
            return;
        saboteur.makeItSticky();
        View.refresh();
    }

    /**
     * A "Mozgás" gomb megnyomását kezeli
     * 
	 * @param evt a gomb megnyomásakor kiváltódott esemény
     */
    private void bMoveActionPerformed(ActionEvent evt) {
        Object lock = new Object();
        var clickThread = new Thread(() -> {
            var selectorPanel = new SelectorPanel(lock);
            View.GAME_WINDOW.setPlayerPanel(selectorPanel);
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException ignored) {
                }
            }
            View.GAME_WINDOW.setPlayerPanel(this);
            if (selectorPanel.selectedComponent != null)
                saboteur.move(selectorPanel.selectedComponent);
            refresh();
            View.refresh();
        });
        clickThread.start();
    }

    /**
     * A "Cső csúszóssá tétele" gomb megnyomását kezeli
     * 
	 * @param evt a gomb megnyomásakor kiváltódott esemény
     */
    private void bSlipperyActionPerformed(ActionEvent evt) {
        if (saboteur == null)
            return;
        saboteur.makeItSlippery();
        refresh();
        View.refresh();
    }

    private void refresh() {
        bRedirect.setEnabled(!saboteur.actionPerformed && saboteur.component != null && saboteur.component instanceof Pump);
        bLeak.setEnabled(!saboteur.actionPerformed && saboteur.component != null && saboteur.component instanceof Pipe pipe && pipe.isLeakable());
        bSticky.setEnabled(!saboteur.actionPerformed && saboteur.component != null && saboteur.component instanceof Pipe pipe && !pipe.isSticky());
        bMove.setEnabled(!saboteur.moved && saboteur.ableToMove);
        bSlippery.setEnabled(!saboteur.actionPerformed && saboteur.component != null && saboteur.component instanceof Pipe pipe && !pipe.isSlippery());
    }
}
