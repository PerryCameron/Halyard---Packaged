package com.ecsail.gui.boxes;

import com.ecsail.enums.MembershipType;
import com.ecsail.HalyardPaths;
import com.ecsail.sql.select.SqlMembership_Id;
import com.ecsail.structures.MemLabelsDTO;
import com.ecsail.structures.MembershipListDTO;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class HBoxMembership extends HBox {
	
	private MembershipListDTO membership;
	private MemLabelsDTO labels;

	public HBoxMembership(MembershipListDTO m, MemLabelsDTO ml) {
		this.membership =  m;
		this.labels = ml;
		
		//////////// OBJECTS //////////
		HBox hbox1 = new HBox(); // sub HBox1 a
        HBox hbox2 = new HBox(); // sub HBox1 b
        HBox hbox3 = new HBox();
        HBox hbox4 = new HBox();
        HBox hbox5 = new HBox();

        labels.getSelectedYear().setText(membership.getSelectedYear());
        labels.getJoinDate().setText(membership.getJoinDate());
        labels.getMemberID().setText("" + membership.getMembershipId());
        labels.getMemberType().setText("" + MembershipType.getByCode(membership.getMemType()));
        labels.getStatus().setText(getStatus());
     
        ///////////// ATTRIBUTES ///////////
        labels.getSelectedYear().setStyle("-fx-font-weight: bold;");
        labels.getJoinDate().setStyle("-fx-font-weight: bold;");
        labels.getMemberID().setStyle("-fx-font-weight: bold;");
        labels.getMemberType().setStyle("-fx-font-weight: bold;");
        labels.getStatus().setStyle("-fx-font-weight: bold;");
        
        hbox5.getChildren().addAll(new Label("Record Year: "),labels.getSelectedYear());
        hbox1.getChildren().addAll(new Label("Membership ID: "),labels.getMemberID());
        hbox2.getChildren().addAll(new Label("Membership Type: "),labels.getMemberType());
        hbox3.getChildren().addAll(new Label("Join Date: "), labels.getJoinDate());
        hbox4.getChildren().addAll(new Label("Status: "),labels.getStatus());
        
        hbox1.setSpacing(5);
        hbox2.setSpacing(5);
        hbox3.setSpacing(5);
        hbox4.setSpacing(5);
        hbox5.setSpacing(5);
        
        hbox1.setAlignment(Pos.CENTER_LEFT);
        hbox2.setAlignment(Pos.CENTER_LEFT);
        hbox3.setAlignment(Pos.CENTER_LEFT);
        hbox4.setAlignment(Pos.CENTER_LEFT);
        hbox5.setAlignment(Pos.CENTER_LEFT);
    
        getChildren().addAll(hbox5,hbox1,hbox2,hbox3);
        
	} // CONSTRUCTOR END
	
	private String getStatus() {
		String result = "not active";
		if(SqlMembership_Id.isRenewedByMsidAndYear(membership.getMsid(), HalyardPaths.getYear())) {
			result = "active";
		}
		return result;
	}

} // CLASS END
