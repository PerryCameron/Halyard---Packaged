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
	
	private final MembershipListDTO membership;

    public HBoxMembership(MembershipListDTO m, MemLabelsDTO ml) {
		this.membership =  m;

        //////////// OBJECTS //////////
        var hbox1 = new HBox(); // sub HBox1 a
        var hbox2 = new HBox(); // sub HBox1 b
        var hbox3 = new HBox();
        var hbox4 = new HBox();
        var hbox5 = new HBox();

        ml.getSelectedYear().setText(membership.getSelectedYear());
        ml.getJoinDate().setText(membership.getJoinDate());
        ml.getMemberID().setText("" + membership.getMembershipId());
        ml.getMemberType().setText("" + MembershipType.getByCode(membership.getMemType()));
//        ml.getMemberType().setText("" + membership.getMemType());
        ml.getStatus().setText(getStatus());
     
        ///////////// ATTRIBUTES ///////////
        ml.getSelectedYear().setStyle("-fx-font-weight: bold;");
        ml.getJoinDate().setStyle("-fx-font-weight: bold;");
        ml.getMemberID().setStyle("-fx-font-weight: bold;");
        ml.getMemberType().setStyle("-fx-font-weight: bold;");
        ml.getStatus().setStyle("-fx-font-weight: bold;");
        
        hbox5.getChildren().addAll(new Label("Record Year: "), ml.getSelectedYear());
        hbox1.getChildren().addAll(new Label("Membership ID: "), ml.getMemberID());
        hbox2.getChildren().addAll(new Label("Membership Type: "), ml.getMemberType());
        hbox3.getChildren().addAll(new Label("Join Date: "), ml.getJoinDate());
        hbox4.getChildren().addAll(new Label("Status: "), ml.getStatus());
        
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
