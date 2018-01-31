/////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//                                        Future Updates/ Notes
//
//
// I might consider taking out the green highlights as it doesn't seem to be as useful as I thought.
//
// The right list seems to be confusing to the user as they might not completely understand why the
// numbers are being sent from the left to right. Possibly with he middle list also. Add headers.
//
// Get rid of the border lines in the middle list.
//
// I've noticed that the TF_Hold_Nums clears the input in the box not matter what. Maybe change that
// because the user might get annoyed if the input keeps clearing and they accidently clicked out of the
// box. Same the the TF_Range fields maybe do something about that. This is not a critical fix but
// something to consider.
//
// Organize arguments so they aren't all placed in sporadically.
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////
package Start_Program;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.CellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


/////////////////////////////////////////////////////////////////
//
// Main - Holds everything thats needed to make the program work.
//
/////////////////////////////////////////////////////////////////
public class Main
{

	////////////////////////////////////////////////////////////////////////
	//
	// main - Calls all the necessary components to get the program to work.
	//
	////////////////////////////////////////////////////////////////////////
	public static void main(String[] args)
	{
		
		// Holds the main frame of the program.
		JFrame Window = Design_Window(Create_Window());
		
		Window.setVisible(true);
		

	}
	
	////////////////////////////////////////////////////////////////////////////
	//
	// Create_Window - Creates the window with the bare basics of what's needed.
	//
	////////////////////////////////////////////////////////////////////////////
	private static JFrame Create_Window()
	{

		// Holds the main frames height and width.
		int Screen_Width = 350;
		int Screen_Height = 700;
		
		// Holds the main frames title.
		String Title = "Number Analyser";
		
		
		JFrame F_Window = new JFrame();
		F_Window.setSize(Screen_Width, Screen_Height);
		F_Window.setTitle(Title);
		F_Window.setResizable(false);
		F_Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		F_Window.setLocationRelativeTo(null);
		
		return F_Window;
		
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Design_Window - Creates all the components to the main frame window and calls another method to add
	//                 the actions and place the elements..
	//
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	private static JFrame Design_Window(JFrame Window)
	{
		
		// Holds all the users entered numbers.
		JTable T_Nums = new JTable(0,1);
		T_Nums.getColumnModel().getColumn(0).setHeaderValue("List");
		
		// Makes a scroll bar for T_Nums.
		JScrollPane SP_Num_Scroll = new JScrollPane(T_Nums, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		SP_Num_Scroll.setPreferredSize(new Dimension((6 * 10 * 2), 500));
		
		// Holds the name of the file to hold all the users numbers.
		String File_Name = "Num_List.txt";

		
		Fill_Table(File_Name, T_Nums);
		
		
		// Holds all the numbers the user selected to compare.
		DefaultListModel<String> DML_Compare = new DefaultListModel<String>();
		JList<String> Li_Compare_Nums = new JList<String>(DML_Compare);
		Li_Compare_Nums.setFixedCellWidth(6 * 10 * 2);
		
		// Holds a count of all the numbers selected. Greatest to least.
		DefaultListModel<String> DML_Common = new DefaultListModel<String>();
		JList<String> Li_Common_Nums = new JList<String>(DML_Common);
		Li_Common_Nums.setBackground(Window.getBackground());
		Li_Common_Nums.setFixedCellWidth(6 * 10);
		
		//Make a scroll bar for Li_Common_Nums.
		JScrollPane SP_Common_Scroll = new JScrollPane(Li_Common_Nums,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		SP_Common_Scroll.setPreferredSize(new Dimension((6 * 10), 500));
		
		// Make a scroll bar for Li_Compare_Nums.
		JScrollPane SP_Compare_Scroll = new JScrollPane(Li_Compare_Nums,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		SP_Compare_Scroll.setPreferredSize(new Dimension((6 * 10 * 2), 500));
		
		
		// Holds the number the user wanted to add.
		JTextField TF_Hold_Num = new JTextField();
		TF_Hold_Num.setPreferredSize(new Dimension((6 * 10 * 2), 20));
		
		// The button to add the users number.
		JButton B_Add = new JButton("Add");
		B_Add.setPreferredSize(new Dimension(60, 20));
		
		
		// This is used in the same line as TF_Range's.
		JLabel L_Range_1 = new JLabel("Select Numbers In The Range Of");
		JLabel L_Range_2 = new JLabel("Through");
		
		// Holds the lines that are selected. The user can use this to select lines if they want.
		JTextField TF_Range_1 = new JTextField(3);
		JTextField TF_Range_2 = new JTextField(3);
		
		
		// Holds the compare and delete buttons on the main frame.
		JButton B_Compare = new JButton("Compare");
		JButton B_Delete = new JButton("Delete");
		

		Create_Actions(T_Nums, DML_Common, DML_Compare, TF_Hold_Num, TF_Range_1, TF_Range_2, B_Add,
				B_Compare, B_Delete, File_Name);
		
		Window.add(Place_Items(SP_Num_Scroll, SP_Common_Scroll, SP_Compare_Scroll, TF_Hold_Num, B_Add,
				L_Range_1, L_Range_2, TF_Range_1, TF_Range_2, B_Compare, B_Delete));
		
		
		return Window;

	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	//
	// Fill_Table - Fills T_Nums in the main frame if the user already had saved numbers.
	//
	/////////////////////////////////////////////////////////////////////////////////////
	private static void Fill_Table(String File_Name, JTable T_Nums)
	{
		
		// Holds file with the users saved numbers.
		//
		// Note: This does not create a new file. This will hold the path to the file and if it doesn't
		//       exist then errors will occur.
		File Num_File = Check_File(File_Name);
		
		
		// Read from the file and populate T_Nums if any numbers are in the file.
		try
		{
			
			DefaultTableModel Model = (DefaultTableModel) T_Nums.getModel();
			Scanner Read_File = new Scanner(Num_File);
			
			
			// Read all lines from file.
			while(Read_File.hasNext())
				Model.addRow(new Object[]{Read_File.nextLine()});

			
			Read_File.close();
			
		}
		catch(Exception Error)
		{
			
			JOptionPane.showMessageDialog(null, "Error Reading File.", "File Broke",
					JOptionPane.INFORMATION_MESSAGE);
			
		}
		
	}
	
	////////////////////////////////////////////////////////////////////////////
	//
	// Create_Actions - Creates all the actions that are used in the main frame.
	//
	////////////////////////////////////////////////////////////////////////////
	private static void Create_Actions(JTable T_Nums, DefaultListModel<String> DLM_Common,
			DefaultListModel<String> DLM_Compare, JTextField TF_Hold_Num, JTextField TF_Range_1,
			JTextField TF_Range_2, JButton B_Add, JButton B_Compare, JButton B_Delete, String File_Name)
	{
		
		///////////////////////////////////////////////////
		//
		//                       T_Nums
		//
		//
		// Value Changed: Change values in TF_Range fields.
		//
		///////////////////////////////////////////////////
		T_Nums.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{

			public void valueChanged(ListSelectionEvent arg0)
			{

				// Only change TF_range fields if T_Nums has focus.
				if(T_Nums.isFocusOwner())
				{
					
					// Holds the min and max range of T_Nums so user can't go out of bounds and to check
					// if the user selects lines in any order.
					int Low_Index = (T_Nums.getSelectedRow() + 1);
					int[] Selected_Eles = T_Nums.getSelectedRows();
					int High_Index = (Selected_Eles.length + (Low_Index - 1));
					
					
					// Change value of TF_Range fields depending on how the user selected the lines.
					if(Selected_Eles[Selected_Eles.length - 1] == (High_Index - 1))
					{
						
						TF_Range_1.setText(Integer.toString(Low_Index));
						TF_Range_2.setText(Integer.toString(High_Index));
					
					}
					else
					{
	
						TF_Range_1.setText("???");
						TF_Range_2.setText("???");
						
					}
				
				}
				
			}

		});

		/////////////////////////////////////////////////////////////////////////////////////////////////
		//
		//                                          T_Nums
		//
		// Focus Gained: Change value in TF_Hold_Num. Validate the users input and if everything is valid
		//               update the file.
		//
		// Focus Lost: Is used as a flag/ error catch which is used in the focus gained. This is only
		//             entered if the user is editing.
		//
		/////////////////////////////////////////////////////////////////////////////////////////////////
		T_Nums.addFocusListener(new FocusAdapter()
		{
		
			// Made global to be used as the flag/ error catch. Holds the data before user started making
			// changes and holds the line that was edited.
			String Temp_Input = null;
			int Index;
			
			public void focusGained(FocusEvent e)
			{
				
				TF_Hold_Num.setText("Enter A Number");
				
				
				// Only enter if the user was editing.
				if(Temp_Input != null)
				{
					
					// Validate the users input and if valid update the file.
					try
					{
						
						// Holds the users edited input.
						String[] Test_Input = ((String) T_Nums.getValueAt(Index, 0)).split(" ");
						
						
						// Test input.
						for(String Val : Test_Input)
							Integer.parseInt(Val);
						
						
						// Holds file with the users saved numbers.
						//
						// Note: This does not create a new file. This will hold the path to the file and
						//       if it doesn't exist then errors will occur.
						File Num_File = new File(File_Name);

						// Erase the file to update.
						Formatter Brand_New_File = new Formatter(File_Name);
						
						Brand_New_File.close();
						
						// Holds the file.
						FileWriter Write_File = new FileWriter(Num_File, true);
						
						
						// Update the file by taking all the lines from T_Nums.
						for(int Rows = 0; Rows < T_Nums.getRowCount(); Rows++)
							Write_File.write(T_Nums.getValueAt(Rows, 0).toString() + "\n");
						
						
						
						Write_File.close();

					}
					catch(IllegalArgumentException Error)
					{
						
						T_Nums.setValueAt(Temp_Input, Index, 0);
						JOptionPane.showMessageDialog(null,
								"Dont Use Letters. Use Spaces To Separate Numbers.", "Invalid Input",
								JOptionPane.INFORMATION_MESSAGE);
						
					}
					catch(Exception Error)
					{
					
						JOptionPane.showMessageDialog(null, "Error Finding File.", "Wheres The File?",
								JOptionPane.INFORMATION_MESSAGE);
					
					}
					
					// Reset the flag to show the user has edited.
					Temp_Input = null;
					
					
					JTableHeader TH = T_Nums.getTableHeader();
					TableColumnModel TCM = TH.getColumnModel();
					TableColumn TC = TCM.getColumn(0);
					TC.setHeaderValue( "List" );
					TH.repaint();
					
				}
				
			}
			
			public void focusLost(FocusEvent e)
			{
				
				// Only create a flag and save the previous data if the user is editing.
				if(T_Nums.isEditing())
				{
					
					// Hold the line being edited line number and data.
					Index = T_Nums.getSelectedRow();
					Temp_Input = (String) T_Nums.getValueAt(Index, 0);
					
					
					// Change the header of the list to kind of show the user that what they entered is
					// not saved yet.
					JTableHeader TH = T_Nums.getTableHeader();
					TableColumnModel TCM = TH.getColumnModel();
					TableColumn TC = TCM.getColumn(0);
					TC.setHeaderValue( "*List" );
					TH.repaint();

				}
				
			}
			
		});

		///////////////////////////////////////////////////////////////////////////////////////
		//
		//           TF_Hold_Nums
		//
		//
		// Focus Gained: Clears the numbers in the TF_Range fields and the selection in T_Nums.
		//
		///////////////////////////////////////////////////////////////////////////////////////
		TF_Hold_Num.addFocusListener(new FocusAdapter()
		{
			
			public void focusGained(FocusEvent e)
			{
				
				T_Nums.clearSelection();
				TF_Hold_Num.setText("");
				
				TF_Range_1.setText("0");
				TF_Range_2.setText("0");
				
			}
			
		});
		
		//////////////////////////////////////////////////////////////////////////////////
		//
		//                                     TF_Range_1
		//
		//
		// Key Released: Highlight the rows if the user wants to type in a specific range.
		//
		// Key Typed: Nothing
		//
		// Key Pressed: Nothing
		//
		//////////////////////////////////////////////////////////////////////////////////
		TF_Range_1.addKeyListener(new KeyListener()
		{

			@Override
			public void keyPressed(KeyEvent e)
			{
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				
				// Highlight the rows the user wants. This does not warn the user of invalid input.
				// Nothing will highlight on invalid input.
				try
				{
					
					CellEditor Cell_Editor = T_Nums.getCellEditor();
					
					if(T_Nums.isEditing())
						Cell_Editor.cancelCellEditing();

					
					// Holds the input in TF_Range_1
					int Range_1 = Integer.parseInt(TF_Range_1.getText());

					
					// Check if users input was out of bounds.
					if(Range_1 < 1)
						Range_1 = 1;
					else if(Range_1 > T_Nums.getRowCount())
						Range_1 = T_Nums.getRowCount();

					T_Nums.changeSelection((Range_1 - 1), 0, false, false);
					
					TF_Range_1.setText(Integer.toString(Range_1));
					
					
					// Holds the input in TF_Range_2
					int Range_2 = Integer.parseInt(TF_Range_2.getText());

					
					// Check if users input was out of bounds.
					if(Range_2 < 1)
						Range_2 = 1;
					else if(Range_2 > T_Nums.getRowCount())
						Range_2 = T_Nums.getRowCount();

					T_Nums.changeSelection((Range_2 - 1), 0, false, true);

				}
				catch(IllegalArgumentException Error)
				{
					//Don't Need To Warn The User About This.
				}

			}

			@Override
			public void keyTyped(KeyEvent e)
			{
			}

		});
		
		//////////////////////////////////////////////////////////////////////////////////
		//
		//                                     TF_Range_2
		//
		//
		// Key Released: Highlight the rows if the user wants to type in a specific range.
		//
		// Key Typed: Nothing
		//
		// Key Pressed: Nothing
		//
		//////////////////////////////////////////////////////////////////////////////////
		TF_Range_2.addKeyListener(new KeyListener()
		{

			@Override
			public void keyPressed(KeyEvent e)
			{
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				
				// Highlight the rows the user wants. This does not warn the user of invalid input.
				// Nothing will highlight on invalid input.
				try
				{
					
					CellEditor Cell_Editor = T_Nums.getCellEditor();
					
					if(T_Nums.isEditing())
						Cell_Editor.cancelCellEditing();
					
					
					// Holds the input in TF_Range_2
					int Range_2 = Integer.parseInt(TF_Range_2.getText());

					
					// Check if users input was out of bounds.
					if(Range_2 < 1)
						Range_2 = 1;
					else if(Range_2 > T_Nums.getRowCount())
						Range_2 = T_Nums.getRowCount();

					T_Nums.changeSelection((Range_2 - 1), 0, false, false);
					
					TF_Range_2.setText(Integer.toString(Range_2));
					
					
					// Holds the input in TF_Range_1
					int Range_1 = Integer.parseInt(TF_Range_1.getText());

					
					// Check if users input was out of bounds.
					if(Range_1 < 1)
						Range_1 = 1;
					else if(Range_1 > T_Nums.getRowCount())
						Range_1 = T_Nums.getRowCount();

					T_Nums.changeSelection((Range_1 - 1), 0, false, true);

				}
				catch(IllegalArgumentException Error)
				{
					//Don't Need To Warn The User About This.
				}

			}

			@Override
			public void keyTyped(KeyEvent e)
			{
			}

		});
		
		////////////////////////////////////////////////////////////////////////
		//
		//                                  B_Add
		//
		//
		// Action Performed: If users input is valid update the file and T_Nums.
		//
		////////////////////////////////////////////////////////////////////////
		B_Add.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e)
			{
				
				// Validate users input and update the file and T_Nums.
				try
				{
					
					// Hold the text in TF_Hold_Num.
					String Hold_Num = TF_Hold_Num.getText();
					
					// Hold all the sections of TF_Hold_Num.
					String[] Temp_Nums = Hold_Num.split(" ");
					
					
					// Validate input.
					for(String Num : Temp_Nums)
						Integer.parseInt(Num);
					
					TF_Hold_Num.setText("");
					
					
					// Used to add new number to T_Nums.
					DefaultTableModel Model = (DefaultTableModel) T_Nums.getModel();
					Model.addRow(new Object[]{Hold_Num});
					
					// Holds file with the users saved numbers.
					//
					// Note: This does not create a new file. This will hold the path to the file and if
					//       it doesn't exist then errors will occur.
					File Num_File = Check_File(File_Name);
					
					
					// Update file.
					try
					{
					
						FileWriter Write_File = new FileWriter(Num_File, true);
						
						Write_File.write(Hold_Num + "\n");
						
						Write_File.close();
					
					}
					catch(Exception E)
					{
					
						JOptionPane.showMessageDialog(null, "Error Finding File.", "Wheres The File?",
								JOptionPane.INFORMATION_MESSAGE);
					
					}

				}
				catch(IllegalArgumentException Error)
				{
					
					JOptionPane.showMessageDialog(null, "Dont Use Letters. Use Spaces To Separate Numbers.",
							"Invalid Input", JOptionPane.INFORMATION_MESSAGE);
					
				}

			}
			
		});
		
		///////////////////////////////////////////////////////////////////////////////////////////////
		//
		//                                          B_Compare
		//
		//
		// Action Performed: Takes all the selected row(s) from T_Nums and copies them over to
		//                   Li_Compare_Nums. Will highlight certain numbers green if they appeared one
		//                   line above them.
		//
		///////////////////////////////////////////////////////////////////////////////////////////////
		B_Compare.addActionListener(new ActionListener()
		{
			
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e)
			{
			
				// Holds the selected rows in T_Nums.
				int[] Hold_Selections = T_Nums.getSelectedRows();
				
				// Holds a row and the row before it for comparison.
				String[] Hold_Sections_1, Hold_Sections_2;
				
				// Holds the modified string from comparison to be put into Li_Compare_Nums.
				String Modded_String = "<html>";
				
				// To indicate the string was modified.
				boolean Flag = true;
				
				// Used to organize the common numbers.
				Map<String, Integer> Common_Nums = new HashMap<String, Integer>();
				
				
				// Only enter if something was selected in T_Nums.
				if(Hold_Selections.length > 0)
				{
					
					DLM_Compare.clear();
					DLM_Compare.addElement("<html>" + (String) T_Nums.getValueAt(Hold_Selections[0], 0) +
							"</html>");
				
					// Look at each section of the row and keep a tally of each number.
					//
					// Note: This loop is special for the first row only.
					for(String Sec : ((String) T_Nums.getValueAt(Hold_Selections[0], 0)).split(" "))
					{
						
						// If number is not in map add it else plus 1 to the tally for that number.
						if(Common_Nums.get(Sec) == null)
							Common_Nums.put(Sec, 1);
						else
							Common_Nums.put(Sec, (Common_Nums.get(Sec) + 1));
						
					}
				
					// Loop through all the rows of the selection.
					for(int Count = 0; Count < (Hold_Selections.length - 1); Count++)
					{
						
						// Used to compare one line to the one before it.
						Hold_Sections_1 = ((String) T_Nums.getValueAt(Hold_Selections[Count], 0))
								.split(" ");	
						Hold_Sections_2 = ((String) T_Nums.getValueAt(Hold_Selections[(Count + 1)], 0))
								.split(" ");
	
						
						// Look at each section of the row and keep a tally of each number.
						for(String Sec_2 : Hold_Sections_2)
						{
							
							// If number is not in map add it else plus 1 to the tally for that number.
							if(Common_Nums.get(Sec_2) == null)
								Common_Nums.put(Sec_2, 1);
							else
								Common_Nums.put(Sec_2, (Common_Nums.get(Sec_2) + 1));
							
							// Modify the string if the number was in the row before it.
							for(String Sec_1 : Hold_Sections_1)
							{
								
								// Modify the number to be green, bold, and underlined.
								if(Sec_2.compareTo(Sec_1) == 0)
								{
									
									Modded_String = Modded_String.concat("<font color='green'><b><u>" +
											Sec_2 + "</u></b></font> ");
									Flag = false;
									break;
									
								}
								
							}
							
							// If the string was modified it was already concatenated so don't add it
							// again.
							if(Flag)
								Modded_String = Modded_String.concat(Sec_2 + " ");
							
							Flag = true;
							
						}
						
						Modded_String.concat("</html>");
						DLM_Compare.addElement(Modded_String);
						Modded_String = "<html>";
						
					}
					
					////////////////////////////////////////////////////////////////////////////////////
					// I REALLY DONT KNOW WHAT THIS IS DOING AT THE MOMENT.       BELOW BELOW BELOW
					///////////////////////////////////////////////////////////////////////////////////
					
					//Common_Nums //= the hash map
					//List<Person> peopleByAge = new ArrayList<Person>(people.values());
					
					List<> asd = ArrayList<>(Common_Nums.values());
					
					/*Object[] Sorted_Vals = Common_Nums.entrySet().toArray();
					
					Arrays.sort(Sorted_Vals, new Comparator()
					{
						
					    public int compare(Object o1, Object o2)
					    {
					    	
					        return ((Map.Entry<String, Integer>) o2).getValue()
					                   .compareTo(((Map.Entry<String, Integer>) o1).getValue());
					        
					    }
					    
					});
					
					DLM_Common.clear();

					for (Object Val : Sorted_Vals)
					{
						
						DLM_Common.addElement(((Map.Entry<String, Integer>) Val).getKey() + " : " +
								((Map.Entry<String, Integer>) Val).getValue());
					    //System.out.println(((Map.Entry<String, Integer>) Val).getKey() + " : "
					           // + ((Map.Entry<String, Integer>) Val).getValue());
					    
					}*/
					
					////////////////////////////////////////////////////////////////////////////////////
					// I REALLY DONT KNOW WHAT THIS IS DOING AT THE MOMENT.       ABOVE ABOVE ABOVE
					///////////////////////////////////////////////////////////////////////////////////
				
				}

			}
			
		});
		
		//////////////////////////////////////////////////////////////////////////////////////
		//
		//                                    B_Delete
		//
		//
		// Action Performed: If rows are selected delete them from T_Nums and update the file.
		//
		//////////////////////////////////////////////////////////////////////////////////////
		B_Delete.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e)
			{
				
				// Holds the selected rows and the model of the list.
				int[] Selected_Eles = T_Nums.getSelectedRows();
				DefaultTableModel Model = (DefaultTableModel) T_Nums.getModel();
				
				
				// Remove items from Li_Common_Nums.
				for(int Index = (Selected_Eles.length - 1); Index >= 0; Index--)
					Model.removeRow(Selected_Eles[Index]);

				
				// Holds file with the users saved numbers.
				//
				// Note: This does not create a new file. This will hold the path to the file and if it
				//       doesn't exist then errors will occur.
				File Num_File = new File(File_Name);

				
				// Erase the file and update by taking items from T_Nums.
				try
				{
					
					// Erase the file.
					Formatter Brand_New_File = new Formatter(File_Name);
					
					Brand_New_File.close();
					
					
					// Hold the file.
					FileWriter Write_File = new FileWriter(Num_File, true);
					
					
					// Loop through T_Nums.
					for(int Index = 0; Index < T_Nums.getRowCount(); Index++)
						Write_File.write(T_Nums.getValueAt(Index, 0).toString() + "\n");

					
					Write_File.close();
					
				}
				catch(Exception Error)
				{
				
					JOptionPane.showMessageDialog(null, "Error Finding File.", "Wheres The File?",
							JOptionPane.INFORMATION_MESSAGE);
				
				}

			}
			
		});

	}

	///////////////////////////////////////////////////////////////////////
	//
	// Place_Items - Places all the items needed to make up the main frame.
	//
	///////////////////////////////////////////////////////////////////////
	private static JPanel Place_Items(JScrollPane SP_Num_Scroll, JScrollPane SP_Common_Scroll,
			JScrollPane SP_Compare_Scroll, JTextField TF_Hold_Num, JButton B_Add, JLabel L_Range_1,
			JLabel L_Range_2, JTextField TF_Range_1, JTextField TF_Range_2, JButton B_Compare,
			JButton B_Delete)
	{
		
		// The main panel.
		JPanel P_Main_Panel = new JPanel(new GridBagLayout());
		
		// Used to organize the main panel.
		GridBagConstraints GBC_Grid_Bag = new GridBagConstraints();
		GBC_Grid_Bag.insets = new Insets(10,10,10,0);
		
		
		int X = 0;
		int Y = 0;
		
		GBC_Grid_Bag.gridx = X;
		GBC_Grid_Bag.gridy = Y;

		P_Main_Panel.add(SP_Num_Scroll, GBC_Grid_Bag);
		
		X++;
		
		
		GBC_Grid_Bag.gridx = X;
		//GBC_Grid_Bag.gridy = Y;

		P_Main_Panel.add(SP_Common_Scroll, GBC_Grid_Bag);
		
		X++;
		
		
		GBC_Grid_Bag.gridx = X;
		//GBC_Grid_Bag.gridy = Y;
		
		P_Main_Panel.add(SP_Compare_Scroll, GBC_Grid_Bag);
		
		Y++;
		X--;X--;
		
		
		GBC_Grid_Bag.gridx = X;
		GBC_Grid_Bag.gridy = Y;

		P_Main_Panel.add(TF_Hold_Num, GBC_Grid_Bag);
		
		X++;
		
		
		GBC_Grid_Bag.gridx = X;
		//GBC_Grid_Bag.gridy = Y;

		P_Main_Panel.add(B_Add, GBC_Grid_Bag);
		
		X--;
		Y++;

		
		// Used to help organize the main panel.
		JPanel P_Sub_Main_Panel = new JPanel();
		FlowLayout Sub_Main_Panel = new FlowLayout();
		
		P_Sub_Main_Panel.setLayout(Sub_Main_Panel);
		
		P_Sub_Main_Panel.add(L_Range_1);
		P_Sub_Main_Panel.add(TF_Range_1);
		P_Sub_Main_Panel.add(L_Range_2);
		P_Sub_Main_Panel.add(TF_Range_2);
		
		
		GBC_Grid_Bag.gridwidth = 3;

		GBC_Grid_Bag.gridx = X;
		GBC_Grid_Bag.gridy = Y;
		
		P_Main_Panel.add(P_Sub_Main_Panel, GBC_Grid_Bag);

		X = 0;
		Y++;

		
		P_Sub_Main_Panel = new JPanel();
		Sub_Main_Panel = new FlowLayout();
		
		P_Sub_Main_Panel.setLayout(Sub_Main_Panel);
		
		P_Sub_Main_Panel.add(B_Compare);
		P_Sub_Main_Panel.add(B_Delete);

		
		GBC_Grid_Bag.gridwidth = 3;
		
		GBC_Grid_Bag.gridx = X;
		GBC_Grid_Bag.gridy = Y;
		
		P_Main_Panel.add(P_Sub_Main_Panel, GBC_Grid_Bag);

		
		return P_Main_Panel;
		
	}
	
	////////////////////////////////////////////////////////
	//
	// Check_File - Will create a file if one doesn't exist.
	//
	////////////////////////////////////////////////////////	
	private static File Check_File(String File_Name)
	{
		
		// Holds file with the users saved numbers.
		//
		// Note: This does not create a new file. This will hold the path to the file and if it doesn't
		//       exist then errors will occur.
		File Num_File = new File(File_Name);
		
		
		// If the file doesn't exist then make one.
		if (!Num_File.exists())
		{
			
			// Create the file.
			try
			{
			
				Formatter Brand_New_File = new Formatter(File_Name);
				
				Brand_New_File.close();
			
			}
			catch(Exception E)
			{
			
				JOptionPane.showMessageDialog(null, "Error Finding File.", "Wheres The File?",
						JOptionPane.INFORMATION_MESSAGE);
			
			}
		
		}
		
		
		return Num_File;
		
	}
	
}