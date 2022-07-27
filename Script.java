package hello.com;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Script {

	private static String sourceCFilePath;
	private static String sourceEFilePath;
	private static String destFilePath;
	private static String destFileName;

	public static void main(String[] args) throws IOException {

		JFrame frame = new JFrame("ScriptCombine���^���y�r���ഫ�{��");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GridLayout gl = new GridLayout(10, 1);
		frame.setLayout(gl);

		JLabel jlTitle = new JLabel("���^���y�r���ഫ�{��");
		jlTitle.setFont(jlTitle.getFont().deriveFont(28f));

		JLabel jlFileC = new JLabel("�п�ܤ���r���ɮצ�m");
		jlFileC.setFont(jlTitle.getFont().deriveFont(16f));

		JLabel jlFileE = new JLabel("�п�ܭ^��r���ɮצ�m");
		jlFileE.setFont(jlTitle.getFont().deriveFont(16f));

		JLabel jlMsg = new JLabel("�T�����");
		jlMsg.setFont(jlTitle.getFont().deriveFont(28f));
		jlMsg.setForeground(Color.RED);

		JTextField jtfName = new JTextField();
		jtfName.setFont(jtfName.getFont().deriveFont(28f));

		JLabel jlName = new JLabel("�ت��r�����ɦW:");
		jlName.setFont(jlTitle.getFont().deriveFont(28f));

		JLabel jlShowName = new JLabel("");
		jlShowName.setFont(jlTitle.getFont().deriveFont(12f));

		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(1, 2));
		jp.add(jlName);
		jp.add(jtfName);

		JButton buttonC = new JButton("�п�ܤ���r����");
		buttonC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fileChooser = new JFileChooser();// �ŧifilechooser
				int returnValue = fileChooser.showOpenDialog(frame);// �s�Xfilechooser
				if (returnValue == JFileChooser.APPROVE_OPTION) // �P�_�O�_����ɮ�
				{
					File selectedFile = fileChooser.getSelectedFile();// ������File
					System.out.println(selectedFile.getName()); // �L�X�ɦW
					System.out.println(selectedFile.getPath());
					sourceCFilePath = selectedFile.getPath();
					jlFileC.setText(sourceCFilePath);
				}
			}
		});

		JButton buttonE = new JButton("�п�ܭ^��r����");
		buttonE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fileChooser = new JFileChooser();// �ŧifilechooser
				int returnValue = fileChooser.showOpenDialog(frame);// �s�Xfilechooser
				if (returnValue == JFileChooser.APPROVE_OPTION) // �P�_�O�_����ɮ�
				{
					File selectedFile = fileChooser.getSelectedFile();// ������File
					System.out.println(selectedFile.getName()); // �L�X�ɦW
					System.out.println(selectedFile.getPath());
					sourceEFilePath = selectedFile.getPath();
					jlFileE.setText(sourceEFilePath);
				}
			}
		});

		JButton buttonFolder = new JButton("�п�ܥت���Ƨ�");
		buttonFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fileChooser = new JFileChooser();// �ŧifilechooser
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = fileChooser.showOpenDialog(frame);// �s�Xfilechooser
				if (returnValue == JFileChooser.APPROVE_OPTION) // �P�_�O�_����ɮ�
				{
					File selectedFile = fileChooser.getSelectedFile();// ������File
					Script.destFilePath = selectedFile.getPath();
					String text = Script.destFilePath + "\\" + Script.destFileName + ".srt";
					jlShowName.setText(text);
				}
			}
		});

		JButton buttonGo = new JButton("�}�l����");
		buttonGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				if (Script.sourceCFilePath == null) {
					jlMsg.setText("�п�ܤ���r����");
					return;
				}

				if (Script.sourceEFilePath == null) {
					jlMsg.setText("�п�ܭ^��r����");
					return;
				}

				if (Script.destFilePath == null) {
					jlMsg.setText("�п�ܥت��ؿ�");
					return;
				}

				if (Script.destFileName == null) {
					jlMsg.setText("�п�J�ت��r�����ɦW");
					return;
				}

				try {
					combine(sourceCFilePath, sourceEFilePath, destFilePath, destFileName);
					jlMsg.setText("���^��r���X�֦��\");
				} catch (Exception e) {
					jlMsg.setText("���楢��,���ˬd�ɮ׮榡:" + e.getMessage());
				}

			}
		});

		jtfName.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				warn();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				warn();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				warn();
			}

			public void warn() {
				Script.destFileName = jtfName.getText();
				String text = Script.destFilePath + "\\" + Script.destFileName + ".srt";
				jlShowName.setText(text);
			}

		});

		frame.setSize(500, 500);
		frame.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		frame.add(jlTitle);
		frame.add(buttonC);
		frame.add(jlFileC);
		frame.add(buttonE);
		frame.add(jlFileE);
		frame.add(buttonFolder);
		frame.add(jp);
		frame.add(jlShowName);
		frame.add(buttonGo);
		frame.add(jlMsg);
		frame.setVisible(true);

	}

	private static void combine(String srcPathC, String srcPathE, String destPath, String destName) throws IOException {

		File doc = new File(srcPathC);

		InputStreamReader read = new InputStreamReader(new FileInputStream(doc), "UTF-8");
		BufferedReader obj = new BufferedReader(read);

		String strng;

		int rotateIndex = 0;

		List<String> timeList = new ArrayList<>();
		List<String> indexList = new ArrayList<>();
		List<String> cScriptList = new ArrayList<>();
		List<String> eScriptList = new ArrayList<>();

		while ((strng = obj.readLine()) != null) {

			if (rotateIndex == 0) {
				indexList.add(strng);
				rotateIndex++;
			} else if (rotateIndex == 1) {
				timeList.add(strng);
				rotateIndex++;
			} else if (rotateIndex == 2) {
				cScriptList.add(strng);
				rotateIndex++;
			} else if (rotateIndex == 3) {
				rotateIndex = 0;
			}

		}

		doc = new File(srcPathE);
		read = new InputStreamReader(new FileInputStream(doc), "UTF-8");
		obj = new BufferedReader(read);
		rotateIndex = 0;

		while ((strng = obj.readLine()) != null) {

			if (rotateIndex == 0) {
				rotateIndex++;
			} else if (rotateIndex == 1) {
				rotateIndex++;
			} else if (rotateIndex == 2) {
				eScriptList.add(strng);
				rotateIndex++;
			} else if (rotateIndex == 3) {
				rotateIndex = 0;
			}

		}

		StringBuffer text = new StringBuffer();

		for (int i = 0; i < indexList.size(); i++) {

			text.append(indexList.get(i));
			text.append("\n");

			text.append(timeList.get(i));
			text.append("\n");
			text.append(cScriptList.get(i));
			text.append("\n");
			text.append(eScriptList.get(i));
			text.append("\n");
			text.append("\n");
		}

		FileWriter fw = new FileWriter(destPath + "/" + destName + ".srt");
		fw.write(text.toString());
		fw.flush();

		fw.close();

	}

}
