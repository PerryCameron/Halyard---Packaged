package com.ecsail;

import com.ecsail.gui.tabs.boatview.TabBoatView;
import com.ecsail.gui.tabs.fee.TabFee;
import com.ecsail.gui.tabs.people.TabPeople;
import com.ecsail.gui.tabs.welcome.HBoxWelcome;
import com.ecsail.gui.dialogues.Dialogue_EnvelopePDF;
import com.ecsail.gui.dialogues.Dialogue_RenewalForm;
import com.ecsail.gui.tabs.*;
import com.ecsail.gui.tabs.database.TabDataBase;
import com.ecsail.gui.tabs.deposits.TabDeposits;
import com.ecsail.gui.tabs.membership.TabMembership;
import com.ecsail.gui.tabs.roster.TabRoster;
import com.ecsail.gui.tabs.welcome.TabWelcome;
import com.ecsail.jotform.TabJotForm;
import com.ecsail.pdf.PDF_BoatReport;
import com.ecsail.sql.select.SqlMembershipList;
import com.ecsail.sql.select.SqlMembership_Id;
import com.ecsail.structures.BoatDTO;
import com.ecsail.structures.MembershipListDTO;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

import static com.ecsail.BaseApplication.tabPane;

public class Launcher extends VBox {


    public static void closeTabs() {
        tabPane.getTabs().clear();
    }

    public Launcher() {
        tabPane = new TabPane();
        tabPane.setId("toolbar-box");
        getChildren().add(tabPane);

    }

    public static void openTabDataBase() {
        if (!tabOpen("Database"))
            tabPane.getTabs().add(new TabDataBase("Database"));
        tabPane.getSelectionModel().select(getTabIndex("Database"));
    }

    public static void openBoatsTab() {
        if (!tabOpen("Boats"))
            tabPane.getTabs().add(new TabBoats("Boats"));
        tabPane.getSelectionModel().select(getTabIndex("Boats"));
    }

    public static void openNotesTab() {
        if (!tabOpen("Notes"))
            tabPane.getTabs().add(new TabNotes("Notes"));
        tabPane.getSelectionModel().select(getTabIndex("Notes"));
    }

    public static void openPeopleTab() {
        if (!tabOpen("People"))
            tabPane.getTabs().add(new TabPeople("People"));
        tabPane.getSelectionModel().select(getTabIndex("People"));
    }

    public static void openFeeTab2() {
        tabPane.getTabs().add(new TabFee("Fees"));
        tabPane.getSelectionModel().select(getTabIndex("Fees"));
    }

    public static void openSlipsTab() {
        if (!tabOpen("Slips"))
            tabPane.getTabs().add(new TabSlips("Slips"));
        tabPane.getSelectionModel().select(getTabIndex("Slips"));
    }

    public static void openRosterTab() {
        if (!tabOpen("Roster")) // is the tab already open??
            tabPane.getTabs().add(new TabRoster(BaseApplication.activeMemberships, BaseApplication.selectedYear));
        tabPane.getSelectionModel().select(getTabIndex("Roster"));
    }

    public static void openBoatViewTab(BoatDTO b) {
        if (!tabOpen("Boat"))
            tabPane.getTabs().add(new TabBoatView("Boat " + b.getBoat_id(), b));
        tabPane.getSelectionModel().select(getTabIndex("Boat " + b.getBoat_id()));
    }

    public static void openWelcomeTab(HBoxWelcome boxWelcome) {
        tabPane.getTabs().add(new TabWelcome(boxWelcome));
    }

    public static void openLoginTab() {
        tabPane.getTabs().add(new TabLogin("Log in"));
    }

    public static void createRenewalForms() {
        new Dialogue_RenewalForm();
    }

    public static void openEnvelopesDialogue() {
        new Dialogue_EnvelopePDF();
    }

    public static void createMembershipTabFromPeopleList(int msid) {
        MembershipListDTO membership = SqlMembershipList.getMembershipFromListWithoutMembershipId(msid);
        Launcher.createInactiveMemberTab(membership);
    }

    //	// used for TabRoster and CreateMembership
    public static void createMembershipTabForRoster(int membershipID, int ms_id) {
        MembershipListDTO membership;
        membership = getMembership(ms_id);
        createOrOpenTab(membership, "Membership");
    }

    // used in BoxSlip
    public static void createTabForBoxSlip(int ms_id) {
        MembershipListDTO membership;
        if (SqlMembership_Id.isRenewedByMsidAndYear(ms_id, HalyardPaths.getYear())) { // membership is active and in our object tree
            membership = getMembership(ms_id);
        } else { // membership is not active and needs to be pulled from the SQL Database
            membership = SqlMembershipList.getMembershipFromList(ms_id, HalyardPaths.getYear());
        }
        Tab membershipTab = new TabMembership(membership);
        tabPane.getTabs().add(membershipTab);
        tabPane.getSelectionModel().select(membershipTab); // focus on tab we are wanting
    }

    // used for TabDeposits
    public static void createTabForDeposits(int ms_id, String year) {  // overload
        MembershipListDTO membership;
        membership = SqlMembershipList.getMembershipFromList(ms_id, year);
        createOrOpenTab(membership, "Membership");
    }

    public static void launchTabFromSlips(int ms_id) {
        MembershipListDTO membership = SqlMembershipList.getMembershipList(ms_id, HalyardPaths.getYear());
        createOrOpenTab(membership, "Membership");
    }

    //	// fills incomplete object with latest information and opens tab.
    public static void createActiveMembershipTab(MembershipListDTO membership) {
        membership = SqlMembershipList.getMembershipFromList(membership.getMsid(), HalyardPaths.getYear());
        createOrOpenTab(membership, "Membership");
    }

    //
    public static void createInactiveMemberTab(MembershipListDTO membership) {
        createOrOpenTab(membership, "MSID");
    }

    public static void createMembershipTabForBOD(int msid, String selectedYear) {
        MembershipListDTO membership = SqlMembershipList.getMembershipList(msid, selectedYear);
        createOrOpenTab(membership, "Membership");
    }

    public static void openBoardTab() {
        tabPane.getTabs().add(new TabBoardMembers("Board"));
        tabPane.getSelectionModel().select(getTabIndex("Board"));
    }

//	public static void openTabStub() {
//		tabPane.getTabs().add(new TabStub("Stub Tab"));
//	}

    public static void openDepositsTab() {
        tabPane.getTabs().add(new TabDeposits("Deposits"));
        tabPane.getSelectionModel().select(getTabIndex("Deposits"));
    }

    //	////////////////  UTILITY METHODS ///////////////////////
//
    private static void createOrOpenTab(MembershipListDTO membership, String label) {
        String tabLabel = "";
        if (label.equals("Membership")) {
            tabLabel = "Membership " + membership.getMembershipId();
        } else if (label.equals("MSID")) {
            tabLabel = "MSID " + membership.getMsid();
        }

        if (!tabOpen(tabLabel)) // is the tab already open??
        {
            Tab newTab = new TabMembership(membership);
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        } else
            tabPane.getSelectionModel().select(getTabIndex(tabLabel)); // focus on tab we are wanting
    }

    public static boolean tabOpen(String tabName) {
        boolean thisTab = false;
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getText().equals(tabName))
                thisTab = true;
        }
        return thisTab;
    }

    //	// gets a row with ms_id
    public static void removeMembershipRow(int ms_id) {
        int count = 0;
        int element = 0;
        for (MembershipListDTO mem : BaseApplication.activeMemberships) {
            if (mem.getMsid() == ms_id) element = count;
            count++;
        }
        BaseApplication.activeMemberships.remove(element);
    }

    //
//	// gets a specific membership with and ms_id
    public static MembershipListDTO getMembership(int ms_id) {
        MembershipListDTO membership = null;
        int element = 0;
        for (MembershipListDTO mem : BaseApplication.activeMemberships) {
            if (mem.getMsid() == ms_id) membership = BaseApplication.activeMemberships.get(element);
            element++;
        }
        return membership;
    }

    public static MembershipListDTO getSubleaser(int ms_id) {  // ms_id here is the subleasee
        MembershipListDTO membership = null;
        int element = 0;
        for (MembershipListDTO mem : BaseApplication.activeMemberships) {
            if (mem.getSubleaser() == ms_id) membership = BaseApplication.activeMemberships.get(element);
            element++;
        }
        return membership;  // returns membership of subleaser
    }

    public static TabPane getTabPane() {
        return tabPane;
    }

    public static void setTabPane(TabPane tabPane) {
        BaseApplication.tabPane = tabPane;
    }

    public static int getTabIndex(String tabName) {
        int result = 0;
        int count = 0;
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getText().equals(tabName))
                result = count;
            count++;
        }
        return result;
    }

    public static void closeTab(String tabName) {
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getText().equals(tabName))
                tabPane.getTabs().remove(tab);
        }
    }

    public static void closeActiveTab() {
        tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedIndex());
    }

    public static void createBoatReport() {
        Thread t = new Thread(() -> {
            new PDF_BoatReport();
        });
        t.start();
    }

    public static void createNonRenews() {
//		tabPane.getTabs().add(new TabNonRenewCreator("Non Renew Creator"));
//		tabPane.getSelectionModel().select(getTabIndex("Non Renew Creator"));
    }

    public static void openJotFormTab() {
        tabPane.getTabs().add(new TabJotForm("JotForm"));
        tabPane.getSelectionModel().select(getTabIndex("JotForm"));
    }

    public static void createMembershipReport() {
//		new PDF_MembershipReport();
    }
}
