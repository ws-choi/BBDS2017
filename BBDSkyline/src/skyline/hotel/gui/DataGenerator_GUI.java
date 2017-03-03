package skyline.hotel.gui;

import data.generator.UniformGen;
import gui.communication.CWS_consumer;
import gui.communication.CWS_producer;
import gui.communication.prod_socket;
import gui.event.My_Event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import skyline.hotel.global.SKY_Constants;

import data.generator.AntiCorrGen;
import data.generator.RandomMeanNormDistGen;

public class DataGenerator_GUI extends JPanel implements CWS_producer, CWS_consumer{
	
	private static final long serialVersionUID = 382433424330867546L;
	SW_UserInterface frame;
	prod_socket DG_TO_SINGLE, SINGLE_TO_DG;
	JCheckBox chckbxFixedPrice ;
	
	int index;
	public DataGenerator_GUI( SW_UserInterface frame ) {
	
		this.frame = frame;
		
		JLabel lblSelectDistribution = new JLabel("Select Distribution");
		add(lblSelectDistribution);
		
		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Uniform Distribution", "Normal Distribution", "Antri-Correlated Distribution"}));
		add(comboBox);
		
		JButton btnGenerateInsert = new JButton("Generate & Insert!");
		btnGenerateInsert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				index = comboBox.getSelectedIndex();
				DG_TO_SINGLE.notify_event(null);
			}
		});
		
		chckbxFixedPrice = new JCheckBox("Fixed Price");
		add(chckbxFixedPrice);
		add(btnGenerateInsert);
	}


	@Override
	public void consume_something(int port, My_Event my_Event) {
		
		switch (port) {
		case SKY_Constants.std:
			
			if(my_Event instanceof BoundsInfo)
			{
				BoundsInfo info = (BoundsInfo) my_Event;
				My_Event result = new My_Event();
				try {
					
					if(chckbxFixedPrice.isSelected()) info.bounds[2][0]=info.bounds[2][1]=200;
					
					switch (index) {
					case 0:
						result.carrier = new UniformGen(info.dim, info.bounds, info.floatEnable);
						break;
						
					case 1:
						result.carrier = new RandomMeanNormDistGen(info.dim, info.bounds, info.floatEnable);
						break;
						
					case 2:
						result.carrier = new AntiCorrGen(info.dim, info.bounds, info.floatEnable, 0, 1);
						break;
						

					default:
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				DG_TO_SINGLE.notify_event(result);
			}
			
			break;

		default:
			break;
		}
		
	}

	@Override
	public void set_socket(int port, prod_socket cws_socket) {

		switch (port) {
		case SKY_Constants.dts:
			DG_TO_SINGLE = cws_socket;			
			break;
			
		case SKY_Constants.std:
			SINGLE_TO_DG = cws_socket;

		default:
			break;
		}		
	}

}
