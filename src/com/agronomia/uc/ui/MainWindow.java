package com.agronomia.uc.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.agronomia.uc.spectrometer.JazScript;
import com.agronomia.uc.spectrometer.LoadModelDialog;
import com.agronomia.uc.spectrometer.ModelReader;
import com.agronomia.uc.spectrometer.TextAreaLogger;

public class MainWindow extends JFrame implements LoadModelDialog.LoadModelDialogAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9081422381847070256L;
	
	LoadModelDialog loadModelDialog;
	
	TextAreaLogger textAreaLogger;
	
	JTextArea content;

	public MainWindow() 
	{
		setTitle("JazScript Generator 1.0.0");
		setSize(900, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JTextArea logger = new JTextArea();
		logger.setEditable(false);
		logger.setFont(new Font("Verdana", Font.ITALIC, 10));

		JScrollPane scrollPaneLogger = new JScrollPane(logger);
		textAreaLogger = new TextAreaLogger(logger);

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

		content = new JTextArea();
		content.setFont(new Font("Consolas", Font.PLAIN, 12));
		content.setBackground(Color.BLACK);
		content.setForeground(Color.GREEN);
		content.setCaretColor(Color.WHITE);
		JScrollPane scrollPaneContent = new JScrollPane(content);
		
		loadModelDialog = new LoadModelDialog(this);
		

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Content", null, scrollPaneContent, "Does nothing");

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				tabbedPane, scrollPaneLogger);

		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(400);
		contentPane.add(splitPane, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);

		JMenuItem itemOpen = new JMenuItem("Load models");
		itemOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				loadModelDialog.setVisible(true);
				
			}

		});
		menuFile.add(itemOpen);

		JMenuItem itemsaveAs = new JMenuItem("Save new script");
		itemsaveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String textareaContent = content.getText();

				JFileChooser fileChooser = new JFileChooser();

				if (fileChooser.showSaveDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) 
				{
					File file = fileChooser.getSelectedFile();

					try 
					{
						BufferedWriter out = new BufferedWriter(new FileWriter(
								file));
						out.write(textareaContent);
						out.close();
					} 
					catch (IOException e1) 
					{
						e1.printStackTrace();
					}

				}

			}

		});
		menuFile.add(itemsaveAs);

		JMenuItem itemExit = new JMenuItem("Exit");
		itemExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});
		menuFile.add(itemExit);

	}

	@Override
	public void onLoadAction() 
	{
		
		JazScript script = new JazScript("Script1", "0.0.1", textAreaLogger);
		script.initialize(loadModelDialog.getReaders());
		script.generate();

		content.setText("");
		content.append(script.getCode());
		
		loadModelDialog.setVisible(false);
		
		
	}

	@Override
	public void onCancelAction() 
	{
		if(loadModelDialog != null)
		{
			loadModelDialog.setVisible(false);
		}		
	}

}
