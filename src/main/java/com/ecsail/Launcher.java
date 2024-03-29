package com.ecsail;

import com.ecsail.repository.implementations.MembershipIdRepositoryImpl;
import com.ecsail.repository.implementations.MembershipRepositoryImpl;
import com.ecsail.repository.interfaces.MembershipIdRepository;
import com.ecsail.repository.interfaces.MembershipRepository;
import com.ecsail.views.tabs.boatlist.TabBoatList;
import com.ecsail.views.tabs.boatview.TabBoatView;
import com.ecsail.views.tabs.fee.TabFee;
import com.ecsail.views.tabs.people.TabPeople;
import com.ecsail.views.tabs.welcome.HBoxWelcome;
import com.ecsail.views.dialogues.Dialogue_EnvelopePDF;
import com.ecsail.views.dialogues.Dialogue_RenewalForm;
import com.ecsail.views.tabs.*;
import com.ecsail.views.tabs.deposits.TabDeposits;
import com.ecsail.views.tabs.membership.TabMembership;
import com.ecsail.views.tabs.roster.TabRoster;
import com.ecsail.views.tabs.welcome.TabWelcome;
import com.ecsail.jotform.TabFormList;
import com.ecsail.pdf.PDF_BoatReport;
import com.ecsail.dto.BoatDTO;
import com.ecsail.dto.MembershipListDTO;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import java.time.Year;

import static com.ecsail.BaseApplication.tabPane;

public class Launcher extends VBox {


    public static void closeTabs() {
        tabPane.getTabs().clear();
    }

    public static MembershipRepository membershipRepository = new MembershipRepositoryImpl();
    public static MembershipIdRepository membershipIdRepository = new MembershipIdRepositoryImpl();

    public Launcher() {
        tabPane = new TabPane();
        tabPane.setId("toolbar-box");
        getChildren().add(tabPane);

    }

    public static void openBoatsTab() {
        if (!tabOpen("Boats"))
            tabPane.getTabs().add(new TabBoatList("Boats"));
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
            tabPane.getTabs().add(new TabRoster(BaseApplication.activeMemberships, String.valueOf(Year.now().getValue())));
        tabPane.getSelectionModel().select(getTabIndex("Roster"));
    }

    public static void openBoatViewTab(BoatDTO b) {
        if (!tabOpen("Boat"))
            tabPane.getTabs().add(new TabBoatView("Boat " + b.getBoatId(), b));
        tabPane.getSelectionModel().select(getTabIndex("Boat " + b.getBoatId()));
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
        MembershipListDTO membership = membershipRepository.getMembershipFromListWithoutMembershipId(msid);
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
        if (membershipIdRepository.isRenewedByMsidAndYear(ms_id, String.valueOf(Year.now().getValue()))) { // membership is active and in our object tree
            membership = getMembership(ms_id);
        } else { // membership is not active and needs to be pulled from the SQL Database
            membership = membershipRepository.getMembershipByMsIdAndYear(ms_id, String.valueOf(Year.now().getValue()));
        }
        Tab membershipTab = new TabMembership(membership);
        tabPane.getTabs().add(membershipTab);
        tabPane.getSelectionModel().select(membershipTab); // focus on tab we are wanting
    }

    // used for TabDeposits
    public static void createTabForDeposits(int msId, String year) {  // overload
        MembershipListDTO membership;
        membership = membershipRepository.getMembershipByMsIdAndYear(msId, year);
        createOrOpenTab(membership, "Membership");
    }

    public static void launchTabStub() {
        tabPane.getTabs().add(new TabStub("TabStub"));
        tabPane.getSelectionModel().select(getTabIndex("TabStub"));
    }

    public static void launchNewYearWizard() {
        tabPane.getTabs().add(new TabNewYearGenerator("New Year Wizard"));
        tabPane.getSelectionModel().select(getTabIndex("New Year Wizard"));
    }

    public static void launchTabFromSlips(int msId) {
        MembershipListDTO membership = membershipRepository.getMembershipByMsIdAndYear(msId, String.valueOf(Year.now().getValue()));
        createOrOpenTab(membership, "Membership");
    }

    //	// fills incomplete object with latest information and opens tab.
    public static void createActiveMembershipTab(MembershipListDTO membershipListDTO) {
        membershipListDTO = membershipRepository.getMembershipByMsIdAndYear(membershipListDTO.getMsId(), String.valueOf(Year.now().getValue()));
        createOrOpenTab(membershipListDTO, "Membership");
    }

    //
    public static void createInactiveMemberTab(MembershipListDTO membership) {
        createOrOpenTab(membership, "MSID");
    }

    public static void createMembershipTabForBOD(int msid, String selectedYear) {
        MembershipListDTO membership = membershipRepository.getMembershipByMsIdAndYear(msid, selectedYear);
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
            tabLabel = "MSID " + membership.getMsId();
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
            if (mem.getMsId() == ms_id) element = count;
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
            if (mem.getMsId() == ms_id) membership = BaseApplication.activeMemberships.get(element);
            element++;
        }
        return membership;
    }

    public static MembershipListDTO getSubleaser(int ms_id) {  // ms_id here is the subleasee
        MembershipListDTO membership = null;
        int element = 0;
        for (MembershipListDTO mem : BaseApplication.activeMemberships) {
            if (mem.getSubLeaser() == ms_id) membership = BaseApplication.activeMemberships.get(element);
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
        tabPane.getTabs().add(new TabFormList("JotForm", tabPane));
        tabPane.getSelectionModel().select(getTabIndex("JotForm"));
    }

    public static void createMembershipReport() {
//		new PDF_MembershipReport();
    }
}
