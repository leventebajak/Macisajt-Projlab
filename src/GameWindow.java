import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

/**
 * GameWindow osztály, a játék megjelenítő ablakot reprezentálja
 */
public class GameWindow extends JPanel {
	/**
	 * Fájl objektum a játék állapotának mentéséhez
	 */
    public static File autosave = new File("autosave.sav");
    
    /**
     * A csőrendszert és a játékosokat megjelenítő pálya 
     */
    public static JPanel map;
    
    /**
     * A soron levő játékos lehetséges akcióit megjelenítő panel 
     */
    private static JPanel playerPanel;
    
    /**
     * Az aktuális kör számát megjelenítő címke
     */
    public JLabel lRounds = new JLabel();
    
    public JMenuBar menuBar = new JMenuBar();
    
    /**
     * A begyűjött víz összmennyíségét megjelenítő címke
     */
    public JLabel lCollectedValue = new JLabel();
    
    /**
     * A kifolyt víz összmennyíségét megjelenítő címke
     */
    public JLabel lLeakedValue = new JLabel();

    /**
     * Korábbi játék betöltésekor használandó konstruktor.
     */
    GameWindow() {
        initComponents();
    }

    /**
     * Új játék indításakor használandó konstruktor.
     *
     * @param plumberNames  szerelők nevei
     * @param saboteurNames szabotőrök nevei
     */
    GameWindow(ArrayList<String> plumberNames, ArrayList<String> saboteurNames) {
        Game.NewGame(plumberNames, saboteurNames);
        initComponents();
    }

    /**
     * Inicializálja a GameWindow komponenseit
     */
    private void initComponents() {
        map = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                View.drawAll(g);
            }
        };
        map.setBackground(View.SECONDARY_COLOR);
        map.setForeground(View.PRIMARY_COLOR);

        playerPanel = new JPanel(new BorderLayout());
        playerPanel.setPreferredSize(new Dimension(300, 500));

        Game.getActivePlayer().drawNameAndButtons(this);

        JPanel infoPanel = new JPanel();
        JSeparator separator = new JSeparator();
        JLabel lCollected = new JLabel();
        JLabel lLeaked = new JLabel();

        menuBar.setPreferredSize(new Dimension(76, 25));
        JMenu mFile = new JMenu();
        JMenuItem miExitToMenu = new JMenuItem();
        JMenuItem miExitProgram = new JMenuItem();
        JMenu mGame = new JMenu();
        JMenuItem miSaveGame = new JMenuItem();
        JMenuItem miLoadGame = new JMenuItem();
        mFile.setText("Fájl");
        miExitToMenu.setText("Kilépés a főmenübe");
        miExitToMenu.addActionListener(this::miExitToMenuActionPerformed);
        mFile.add(miExitToMenu);
        miExitProgram.setText("Kilépés a programból");
        miExitProgram.addActionListener(this::miExitProgramActionPerformed);
        mFile.add(miExitProgram);
        menuBar.add(mFile);
        mGame.setText("Játék");
        miSaveGame.setText("Mentés");
        miSaveGame.addActionListener(this::miSaveGameActionPerformed);
        mGame.add(miSaveGame);
        miLoadGame.setText("Betöltés");
        miLoadGame.addActionListener(this::miLoadGameActionPerformed);
        mGame.add(miLoadGame);
        menuBar.add(mGame);

        setBackground(View.PRIMARY_COLOR);
        setForeground(View.SECONDARY_COLOR);
        setPreferredSize(new Dimension(1280, 720));

        infoPanel.setBackground(View.PRIMARY_COLOR);
        infoPanel.setForeground(View.SECONDARY_COLOR);
        infoPanel.setPreferredSize(new Dimension(300, 720));

        separator.setForeground(View.SECONDARY_COLOR);

        lRounds.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lRounds.setForeground(View.SECONDARY_COLOR);
        lRounds.setHorizontalAlignment(SwingConstants.CENTER);

        lCollected.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
        lCollected.setForeground(View.SECONDARY_COLOR);
        lCollected.setHorizontalAlignment(SwingConstants.LEFT);
        lCollected.setText("Kifolyt vízmennyiség:");

        lCollectedValue.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
        lCollectedValue.setForeground(View.SECONDARY_COLOR);
        lCollectedValue.setHorizontalAlignment(SwingConstants.RIGHT);

        lLeaked.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
        lLeaked.setForeground(View.SECONDARY_COLOR);
        lLeaked.setHorizontalAlignment(SwingConstants.LEFT);
        lLeaked.setText("Összegyűjtött vízmennyiség:");

        lLeakedValue.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
        lLeakedValue.setForeground(View.SECONDARY_COLOR);
        lLeakedValue.setHorizontalAlignment(SwingConstants.RIGHT);

        // Generated by NetBeans
        GroupLayout jPanel2Layout = new GroupLayout(infoPanel);
        infoPanel.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap(14, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(lLeaked, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lCollectedValue, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(lCollected, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lLeakedValue, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(separator, GroupLayout.Alignment.TRAILING)
                                        .addComponent(lRounds, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(playerPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(lRounds, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lLeaked, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lCollectedValue, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lCollected, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lLeakedValue, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(separator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(playerPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14))
        );
        GroupLayout MapLayout = new GroupLayout(map);
        map.setLayout(MapLayout);
        MapLayout.setHorizontalGroup(
                MapLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 954, Short.MAX_VALUE)
        );
        MapLayout.setVerticalGroup(
                MapLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(infoPanel, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(map, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(map, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(infoPanel, GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        // End of generated code

        refresh();
    }

    /**
     * Beállítja az új játékos panelt
     * 
     * @param panel az új panel
     */
    public void setPlayerPanel(JPanel panel) {
        playerPanel.removeAll();
        playerPanel.add(panel, BorderLayout.CENTER);
        View.refresh();
    }

    /**
     * A "Kilépés a főmenübe" menüpont megnyomását kezeli
     * 
	 * @param evt a menüpont megnyomásakor kiváltódott esemény
     */
    private void miExitToMenuActionPerformed(ActionEvent evt) {
        Game.SaveGame(autosave);
        View.FRAME.setJMenuBar(null);
        View.setContentPane(View.MAIN_MENU_WINDOW);
        View.MAIN_MENU_WINDOW.bContinueGame.setEnabled(GameWindow.autosave.exists());
    }

    /**
     * A "Kilépés a programból" menüpont megnyomását kezeli
     * 
	 * @param evt a menüpont megnyomásakor kiváltódott esemény
     */
    private void miExitProgramActionPerformed(ActionEvent evt) {
        Game.SaveGame(autosave);
        View.FRAME.dispose();
    }

    /**
     * A "Mentés" menüpont megnyomását kezeli
     * 
	 * @param evt a menüpont megnyomásakor kiváltódott esemény
     */
    private void miSaveGameActionPerformed(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser(new File("").getAbsolutePath());
        fileChooser.setDialogTitle("Adja meg a fájl nevés tés elérési útját");
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
            Game.SaveGame(fileChooser.getSelectedFile());
    }

    /**
     * A "Betöltés" menüpont megnyomását kezeli
     * 
	 * @param evt a menüpont megnyomásakor kiváltódott esemény
     */
    private void miLoadGameActionPerformed(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser(new File("").getAbsolutePath());
        fileChooser.setDialogTitle("Válassza ki a korábban mentett játékot");
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION
                && Game.LoadGame(fileChooser.getSelectedFile())) {
            View.GAME_WINDOW = new GameWindow();
            View.setContentPane(View.GAME_WINDOW);
            View.FRAME.setJMenuBar(View.GAME_WINDOW.menuBar);
        }
    }

    /**
     * Frissíti a panelen megjelenő információkat
     */
    public void refresh() {
        lRounds.setText(Game.getRound() + ". Kör");
        lCollectedValue.setText(String.valueOf(Game.Instance.pipelineSystem.getCollectedWater()));
        lLeakedValue.setText(String.valueOf(Game.Instance.pipelineSystem.getLeakedWater()));
    }
}
