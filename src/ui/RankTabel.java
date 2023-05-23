package ui;

import edu.hitsz.DAOP.Rank;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RankTabel {
    private JPanel mainPanel;
    private JTable scoreTable;
    private JLabel headerLabel;
    private JScrollPane tabelScorePenel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JButton deleteButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tabel");
        frame.setContentPane(new RankTabel().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public RankTabel() {
        String[] columnName = {"名次","玩家名","得分","记录时间"};
        Rank rank=new Rank();
        rank.readBookDaoIml(StartMenu.getFile_name());
        String[][] tableData=rank.returnAllData();
        //表格模型
        DefaultTableModel model = new DefaultTableModel(tableData, columnName){
            @Override
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };

        scoreTable.setModel(model);
        tabelScorePenel.setViewportView(scoreTable);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = scoreTable.getSelectedRow();
                System.out.println(row);
                int result = JOptionPane.showConfirmDialog(deleteButton,
                        "是否确定中删除？");
                if (JOptionPane.YES_OPTION == result && row != -1) {
                    model.removeRow(row);
                }
                Rank rank=new Rank();
                rank.readBookDaoIml(StartMenu.getFile_name());
                rank.getBookDaoIml().doDelete(row);
                rank.writeBookDaoIml(StartMenu.getFile_name());
                updata(rank);
            }
        });
    }
    public void updata(Rank rank){
        String[] columnName = {"名次","玩家名","得分","记录时间"};
        String[][] tableData=rank.returnAllData();
        DefaultTableModel model = new DefaultTableModel(tableData, columnName){
            @Override
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };

        scoreTable.setModel(model);
        tabelScorePenel.setViewportView(scoreTable);
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
