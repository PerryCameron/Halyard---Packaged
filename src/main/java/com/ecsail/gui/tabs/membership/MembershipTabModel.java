package com.ecsail.gui.tabs.membership;

import com.ecsail.dto.*;
import com.ecsail.gui.common.Note;
import com.ecsail.repository.implementations.BoatRepositoryImpl;
import com.ecsail.repository.implementations.InvoiceRepositoryImpl;
import com.ecsail.repository.implementations.MemoRepositoryImpl;
import com.ecsail.repository.implementations.PersonRepositoryImpl;
import com.ecsail.repository.interfaces.BoatRepository;
import com.ecsail.repository.interfaces.InvoiceRepository;
import com.ecsail.repository.interfaces.MemoRepository;
import com.ecsail.repository.interfaces.PersonRepository;
import com.ecsail.sql.select.SqlMembership_Id;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TabPane;

public class MembershipTabModel {

    private PersonRepository personRepository;
    private BoatRepository boatRepository;
    private MemoRepository memoRepository;
    private InvoiceRepository invoiceRepository;
    ObservableList<MemoDTO> memos;
    private MembershipListDTO membership;
    private ObservableList<PersonDTO> people;
    private TabPane fiscalTabPane;
    private TabPane peopleTabPane;
    private Note note;
    private MemLabelsDTO labels;
    private ObservableList<InvoiceDTO> invoices;
    private ObservableList<MembershipIdDTO> id;
    private ObservableList<BoatDTO> boats;

    public MembershipTabModel(MembershipListDTO membershipListDTO) {
        this.personRepository = new PersonRepositoryImpl();
        this.boatRepository = new BoatRepositoryImpl();
        this.memoRepository  = new MemoRepositoryImpl();
        this.invoiceRepository  = new InvoiceRepositoryImpl();
        this.membership = membershipListDTO;
        this.memos = FXCollections.observableArrayList(memoRepository.getMemosByMsId(membership.getMsId()));
        this.note = new Note(memos,membership.getMsId());
        this.people = FXCollections.observableList(personRepository.getActivePeopleByMsId(membership.getMsId()));
        this.invoices = FXCollections.observableArrayList(invoiceRepository.getInvoicesByMsid(membership.getMsId()));
        this.id = FXCollections.observableArrayList(param -> new Observable[]{param.isRenewProperty()});
        this.id.addAll(SqlMembership_Id.getIds(membership.getMsId()));
        this.boats = FXCollections.observableArrayList(boatRepository.getBoatsByMsId(membership.getMsId()));
        this.fiscalTabPane  = new TabPane();
        this.peopleTabPane = new TabPane();
        this.labels = new MemLabelsDTO();
    }

    public MembershipTabModel() {
    }

    public PersonRepository getPersonRepository() {
        return personRepository;
    }

    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public BoatRepository getBoatRepository() {
        return boatRepository;
    }

    public void setBoatRepository(BoatRepository boatRepository) {
        this.boatRepository = boatRepository;
    }

    public MemoRepository getMemoRepository() {
        return memoRepository;
    }

    public void setMemoRepository(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public InvoiceRepository getInvoiceRepository() {
        return invoiceRepository;
    }

    public void setInvoiceRepository(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public MembershipListDTO getMembership() {
        return membership;
    }

    public void setMembership(MembershipListDTO membership) {
        this.membership = membership;
    }

    public ObservableList<PersonDTO> getPeople() {
        return people;
    }

    public void setPeople(ObservableList<PersonDTO> people) {
        this.people = people;
    }

    public TabPane getFiscalTabPane() {
        return fiscalTabPane;
    }

    public void setFiscalTabPane(TabPane fiscalTabPane) {
        this.fiscalTabPane = fiscalTabPane;
    }

    public TabPane getPeopleTabPane() {
        return peopleTabPane;
    }

    public void setPeopleTabPane(TabPane peopleTabPane) {
        this.peopleTabPane = peopleTabPane;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public MemLabelsDTO getLabels() {
        return labels;
    }

    public void setLabels(MemLabelsDTO labels) {
        this.labels = labels;
    }

    public ObservableList<InvoiceDTO> getInvoices() {
        return invoices;
    }

    public void setInvoices(ObservableList<InvoiceDTO> invoices) {
        this.invoices = invoices;
    }

    public ObservableList<MembershipIdDTO> getId() {
        return id;
    }

    public void setId(ObservableList<MembershipIdDTO> id) {
        this.id = id;
    }

    public ObservableList<BoatDTO> getBoats() {
        return boats;
    }

    public void setBoats(ObservableList<BoatDTO> boats) {
        this.boats = boats;
    }
}
