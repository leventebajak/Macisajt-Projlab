import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

public class PlumberPanel extends JPanel {
    private final Plumber plumber;

    public PlumberPanel(Plumber plumber) {
        this.plumber = plumber;
        initComponents();
    }

    private void initComponents() {

        JLabel lName = new JLabel();
        JLabel lTeam = new JLabel();
        JButton bEndRound = new JButton();
        JButton bGrabPipe = new JButton();
        JButton bPlacePipe = new JButton();
        JButton bRedirect = new JButton();
        JButton pPlacePump = new JButton();
        JButton bLeak = new JButton();
        JButton bSticky = new JButton();
        JButton bMove = new JButton();
        JButton bRepair = new JButton();

        setBackground(View.PRIMARY_COLOR);
        setForeground(View.SECONDARY_COLOR);
        setPreferredSize(new Dimension(300, 500));

        lName.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lName.setForeground(View.SECONDARY_COLOR);
        lName.setHorizontalAlignment(SwingConstants.CENTER);
        lName.setText(plumber.name);

        lTeam.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lTeam.setForeground(View.SECONDARY_COLOR);
        lTeam.setHorizontalAlignment(SwingConstants.CENTER);
        lTeam.setText("SZERELŐ");

        bEndRound.setBackground(View.SECONDARY_COLOR);
        bEndRound.setFont(new Font("Segoe UI", Font.BOLD, 18));
        bEndRound.setForeground(View.PRIMARY_COLOR);
        bEndRound.setText("KÖR VÉGE");
        bEndRound.addActionListener(this::bEndRoundActionPerformed);

        bGrabPipe.setBackground(View.SECONDARY_COLOR);
        bGrabPipe.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bGrabPipe.setForeground(View.PRIMARY_COLOR);
        bGrabPipe.setText("Cső felvétele");
        bGrabPipe.addActionListener(this::bGrabPipeActionPerformed);
        bGrabPipe.setEnabled(!plumber.actionPerformed && plumber.component != null && plumber.component instanceof Pump && plumber.grabbedPipe == null);

        bPlacePipe.setBackground(View.SECONDARY_COLOR);
        bPlacePipe.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bPlacePipe.setForeground(View.PRIMARY_COLOR);
        bPlacePipe.setText("Cső lerakása");
        bPlacePipe.addActionListener(this::bPlacePipeActionPerformed);
        bPlacePipe.setEnabled(!plumber.actionPerformed && plumber.component != null && plumber.component instanceof Pump && plumber.grabbedPipe != null);

        bRedirect.setBackground(View.SECONDARY_COLOR);
        bRedirect.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bRedirect.setForeground(View.PRIMARY_COLOR);
        bRedirect.setText("<html><p style=\"text-align:center\">Pumpa<br>átirányítása</p></html>");
        bRedirect.addActionListener(this::bRedirectActionPerformed);
        bRedirect.setEnabled(!plumber.actionPerformed && plumber.component != null && plumber.component instanceof Pump && plumber.grabbedPipe == null);

        pPlacePump.setBackground(View.SECONDARY_COLOR);
        pPlacePump.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pPlacePump.setForeground(View.PRIMARY_COLOR);
        pPlacePump.setText("<html><p style=\"text-align:center\">Pumpa<br>lerakása</p></html>");
        pPlacePump.addActionListener(this::pPlacePumpActionPerformed);
        pPlacePump.setEnabled(!plumber.actionPerformed && plumber.component != null && plumber.component instanceof Pipe && plumber.grabbedPipe == null && plumber.grabbedPump != null);

        bLeak.setBackground(View.SECONDARY_COLOR);
        bLeak.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bLeak.setForeground(View.PRIMARY_COLOR);
        bLeak.setText("Cső lyukasztása");
        bLeak.addActionListener(this::bLeakActionPerformed);
        bLeak.setEnabled(!plumber.actionPerformed && plumber.component != null && plumber.component instanceof Pipe && plumber.grabbedPipe == null);

        bSticky.setBackground(View.SECONDARY_COLOR);
        bSticky.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bSticky.setForeground(View.PRIMARY_COLOR);
        bSticky.setText("<html><p style=\"text-align:center\">Cső ragadóssá<br>tétele</p></html>");
        bSticky.addActionListener(this::bStickyActionPerformed);
        bSticky.setEnabled(!plumber.actionPerformed && plumber.component != null && plumber.component instanceof Pipe && plumber.grabbedPipe == null);

        bMove.setBackground(View.SECONDARY_COLOR);
        bMove.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bMove.setForeground(View.PRIMARY_COLOR);
        bMove.setText("Mozgás");
        bMove.addActionListener(this::bMoveActionPerformed);
        bMove.setEnabled(!plumber.moved);

        bRepair.setBackground(View.SECONDARY_COLOR);
        bRepair.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bRepair.setForeground(View.PRIMARY_COLOR);
        bRepair.setText("Javítás");
        bRepair.addActionListener(this::bRepairActionPerformed);
        bRepair.setEnabled(!plumber.actionPerformed && plumber.component != null && (plumber.component instanceof Pipe || plumber.component instanceof Pump) && plumber.grabbedPipe == null);

        // Generated by NetBeans
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(bEndRound, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lTeam, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lName, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(bMove, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bRepair, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(bLeak, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(bSticky, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(bGrabPipe, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(bPlacePipe, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(bRedirect, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(pPlacePump, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(lName)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lTeam)
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(bMove, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bRepair, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(bLeak, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bSticky, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(bGrabPipe, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bPlacePipe, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(bRedirect, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(pPlacePump, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                .addComponent(bEndRound, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
        );
        // End of generated code
    }

    private void bEndRoundActionPerformed(ActionEvent evt) {
        Game.nextPlayer();
        Game.getActivePlayer().drawNameAndButtons(View.GAME_WINDOW);
        View.refresh();
    }

    private void bRedirectActionPerformed(ActionEvent evt) {
        // TODO: pumpa átirányítása kattintással
        View.GAME_WINDOW.setPlayerPanel(new SelectorPanel(this));
        View.refresh();
    }

    private void bLeakActionPerformed(ActionEvent evt) {
        if (plumber == null)
            return;
        plumber.leak();
        View.refresh();
    }

    private void bStickyActionPerformed(ActionEvent evt) {
        if (plumber == null)
            return;
        plumber.makeItSticky();
        View.refresh();
    }

    private void bMoveActionPerformed(ActionEvent evt) {
        // TODO: mozgás kattintással
        View.GAME_WINDOW.setPlayerPanel(new SelectorPanel(this));
        View.refresh();
    }

    private void bGrabPipeActionPerformed(ActionEvent evt) {
        // TODO: cső felvétele kattintással
        View.GAME_WINDOW.setPlayerPanel(new SelectorPanel(this));
        View.refresh();
    }

    private void bPlacePipeActionPerformed(ActionEvent evt) {
        if (plumber == null)
            return;
        plumber.placePipe();
        View.refresh();
    }

    private void pPlacePumpActionPerformed(ActionEvent evt) {
        if (plumber == null)
            return;
        plumber.placePump();
        View.refresh();
    }

    private void bRepairActionPerformed(ActionEvent evt) {
        if (plumber == null)
            return;
        plumber.repair();
        View.refresh();
    }
}
