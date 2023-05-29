import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class NewGameWindow extends JPanel {

    public NewGameWindow() {
        initComponents();
    }

    private JTable plumberTable;
    private JTable saboteurTable;

    private void initComponents() {
        plumberTable = new JTable();
        saboteurTable = new JTable();
        JPanel panel = new JPanel();
        JButton bBack = new JButton();
        JLabel lTitle = new JLabel();
        JLabel lSaboteurs = new JLabel();
        JLabel lPlumbers = new JLabel();
        JScrollPane jScrollPanePlumbers = new JScrollPane();
        JScrollPane jScrollPaneSaboteurs = new JScrollPane();
        JButton bNewPlumber = new JButton();
        JButton bDeletePlumber = new JButton();
        JButton bNewSaboteur = new JButton();
        JButton bDeleteSaboteur = new JButton();
        JButton bStartGame = new JButton();

        panel.setBackground(View.PRIMARY_COLOR);
        panel.setForeground(View.SECONDARY_COLOR);

        bBack.setBackground(View.SECONDARY_COLOR);
        bBack.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18)); // NOI18N
        bBack.setForeground(View.PRIMARY_COLOR);
        bBack.setText("VISSZA");
        bBack.setActionCommand("VISSZA");
        bBack.addActionListener(this::bBackActionPerformed);

        lTitle.setFont(new Font("Segoe UI", Font.BOLD, 48)); // NOI18N
        lTitle.setForeground(View.SECONDARY_COLOR);
        lTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lTitle.setText("ÚJ JÁTÉK");

        lSaboteurs.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 30)); // NOI18N
        lSaboteurs.setForeground(View.SECONDARY_COLOR);
        lSaboteurs.setHorizontalAlignment(SwingConstants.CENTER);
        lSaboteurs.setText("Szabotőrök:");

        lPlumbers.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 30)); // NOI18N
        lPlumbers.setForeground(View.SECONDARY_COLOR);
        lPlumbers.setHorizontalAlignment(SwingConstants.CENTER);
        lPlumbers.setText("Szerelők:");

        var renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        plumberTable.setBackground(View.PRIMARY_COLOR);
        plumberTable.setForeground(View.SECONDARY_COLOR);
        plumberTable.setRowHeight(25);
        plumberTable.setModel(new DefaultTableModel(
                new Object[][]{{"Szerelő 1"}, {"Szerelő 2"}}, new String[]{"Szerelők"}));
        plumberTable.setSelectionBackground(View.SECONDARY_COLOR);
        plumberTable.setSelectionForeground(View.PRIMARY_COLOR);
        plumberTable.setShowHorizontalLines(true);
        plumberTable.setTableHeader(null);
        plumberTable.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        plumberTable.getColumnModel().getColumn(0).setCellRenderer(renderer);
        plumberTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        plumberTable.setShowHorizontalLines(false);
        plumberTable.setShowVerticalLines(false);
        jScrollPanePlumbers.setBorder(BorderFactory.createEmptyBorder());
        jScrollPanePlumbers.setViewportView(plumberTable);
        jScrollPanePlumbers.getViewport().setBackground(View.PRIMARY_COLOR);

        saboteurTable.setBackground(View.PRIMARY_COLOR);
        saboteurTable.setForeground(View.SECONDARY_COLOR);
        saboteurTable.setRowHeight(25);
        saboteurTable.setModel(new DefaultTableModel(
                new Object[][]{{"Szabotőr 1"}, {"Szabotőr 2"}}, new String[]{"Szabotőrök"}));
        saboteurTable.setSelectionBackground(View.SECONDARY_COLOR);
        saboteurTable.setSelectionForeground(View.PRIMARY_COLOR);
        saboteurTable.setShowHorizontalLines(true);
        saboteurTable.setTableHeader(null);
        saboteurTable.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        saboteurTable.getColumnModel().getColumn(0).setCellRenderer(renderer);
        saboteurTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        saboteurTable.setShowHorizontalLines(false);
        saboteurTable.setShowVerticalLines(false);
        jScrollPaneSaboteurs.setBorder(BorderFactory.createEmptyBorder());
        jScrollPaneSaboteurs.setViewportView(saboteurTable);
        jScrollPaneSaboteurs.getViewport().setBackground(View.PRIMARY_COLOR);

        bNewPlumber.setBackground(View.SECONDARY_COLOR);
        bNewPlumber.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18)); // NOI18N
        bNewPlumber.setForeground(View.PRIMARY_COLOR);
        bNewPlumber.setText("ÚJ");
        bNewPlumber.addActionListener(this::bNewPlumberActionPerformed);

        bDeletePlumber.setBackground(View.SECONDARY_COLOR);
        bDeletePlumber.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18)); // NOI18N
        bDeletePlumber.setForeground(View.PRIMARY_COLOR);
        bDeletePlumber.setText("TÖRLÉS");
        bDeletePlumber.addActionListener(this::bDeletePlumberActionPerformed);

        bNewSaboteur.setBackground(View.SECONDARY_COLOR);
        bNewSaboteur.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18)); // NOI18N
        bNewSaboteur.setForeground(View.PRIMARY_COLOR);
        bNewSaboteur.setText("ÚJ");
        bNewSaboteur.addActionListener(this::bNewSaboteurActionPerformed);

        bDeleteSaboteur.setBackground(View.SECONDARY_COLOR);
        bDeleteSaboteur.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18)); // NOI18N
        bDeleteSaboteur.setForeground(View.PRIMARY_COLOR);
        bDeleteSaboteur.setText("TÖRLÉS");
        bDeleteSaboteur.addActionListener(this::bDeleteSaboteurActionPerformed);

        bStartGame.setBackground(View.SECONDARY_COLOR);
        bStartGame.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18)); // NOI18N
        bStartGame.setForeground(View.PRIMARY_COLOR);
        bStartGame.setText("JÁTÉK INDÍTÁSA");
        bStartGame.addActionListener(this::bStartGameActionPerformed);

        // Generated by NetBeans
        GroupLayout jPanel1Layout = new GroupLayout(panel);
        panel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(139, 139, 139)
                                .addComponent(bNewPlumber, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(bDeletePlumber, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bNewSaboteur, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(bDeleteSaboteur, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
                                .addGap(133, 133, 133))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(bBack, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lPlumbers, GroupLayout.PREFERRED_SIZE, 540, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lSaboteurs, GroupLayout.PREFERRED_SIZE, 540, GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(139, 139, 139)
                                .addComponent(jScrollPanePlumbers, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPaneSaboteurs, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE)
                                .addGap(133, 133, 133))
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bStartGame, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE)
                                .addGap(399, 399, 399))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(bBack, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lTitle, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lSaboteurs, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lPlumbers, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPaneSaboteurs, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPanePlumbers, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(bNewPlumber, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bDeletePlumber, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bNewSaboteur, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bDeleteSaboteur, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                .addGap(52, 52, 52)
                                .addComponent(bStartGame, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(52, Short.MAX_VALUE))
        );
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        // End of generated code
    }

    private void bStartGameActionPerformed(ActionEvent evt) {
        ArrayList<String> plumberNames = new ArrayList<>();
        DefaultTableModel plumberNamesTableModel = (DefaultTableModel) plumberTable.getModel();
        for (int i = 0; i < plumberNamesTableModel.getRowCount(); i++)
            plumberNames.add((String) plumberNamesTableModel.getValueAt(i, 0));

        ArrayList<String> saboteurNames = new ArrayList<>();
        DefaultTableModel saboteurTableModel = (DefaultTableModel) saboteurTable.getModel();
        for (int i = 0; i < saboteurTableModel.getRowCount(); i++)
            saboteurNames.add((String) saboteurTableModel.getValueAt(i, 0));

        View.GAME_WINDOW = new GameWindow(plumberNames, saboteurNames);
        View.setContentPane(View.GAME_WINDOW);
        View.FRAME.setJMenuBar(View.GAME_WINDOW.menuBar);
    }

    private void bDeleteSaboteurActionPerformed(ActionEvent evt) {
        if (saboteurTable.getSelectedRow() != -1 && saboteurTable.getRowCount() > 2)
            ((DefaultTableModel) saboteurTable.getModel()).removeRow(saboteurTable.getSelectedRow());
    }

    private void bNewSaboteurActionPerformed(ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) saboteurTable.getModel();
        int n = 1;
        for (int i = 0; i < model.getRowCount(); i++)
            if (Objects.equals(model.getValueAt(i, 0), "Szabotőr " + n)) {
                n++;
                i = -1;
            }
        model.addRow(new Object[]{"Szabotőr " + n});
    }

    private void bDeletePlumberActionPerformed(ActionEvent evt) {
        if (plumberTable.getSelectedRow() != -1 && plumberTable.getRowCount() > 2)
            ((DefaultTableModel) plumberTable.getModel()).removeRow(plumberTable.getSelectedRow());
    }

    private void bNewPlumberActionPerformed(ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) plumberTable.getModel();
        int n = 1;
        for (int i = 0; i < model.getRowCount(); i++)
            if (Objects.equals(model.getValueAt(i, 0), "Szerelő " + n)) {
                n++;
                i = -1;
            }
        model.addRow(new Object[]{"Szerelő " + n});
    }

    private void bBackActionPerformed(ActionEvent evt) {
        View.setContentPane(View.MAIN_MENU_WINDOW);
    }
}