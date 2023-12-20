package com.ecsail.models;

import com.ecsail.dto.*;
import com.ecsail.repository.implementations.*;
import com.ecsail.repository.interfaces.*;
import com.ecsail.views.common.Note;
import com.ecsail.sql.select.SqlMembership_Id;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TabPane;

public class MembershipTabModel {
    private MembershipListDTO membership;
    private MembershipIdRepository membershipIdRepository;
    private PersonRepository personRepository;
    private BoatRepository boatRepository;
    private MemoRepository memoRepository;
    private InvoiceRepository invoiceRepository;
    private ObservableList<MemoDTO> memos;
    private ObservableList<PersonDTO> people;
    private TabPane fiscalTabPane;
    private TabPane peopleTabPane;
    private Note note;
    private MemLabelsDTO labels;
    private ObservableList<InvoiceDTO> invoices;
    private ObservableList<MembershipIdDTO> membershipIdDTOS;
    private ObservableList<BoatDTO> boats;

    public MembershipTabModel(MembershipListDTO membershipListDTO) {
        this.membership = membershipListDTO;
        this.personRepository = new PersonRepositoryImpl();
        this.boatRepository = new BoatRepositoryImpl();
        this.memoRepository  = new MemoRepositoryImpl();
        this.invoiceRepository  = new InvoiceRepositoryImpl();
        this.membershipIdRepository = new MembershipIdRepositoryImpl();
        this.memos = FXCollections.observableArrayList(memoRepository.getMemosByMsId(membership.getMsId()));
        this.note = new Note(memos,membership.getMsId());
        this.people = FXCollections.observableList(personRepository.getActivePeopleByMsId(membership.getMsId()));
        this.invoices = FXCollections.observableArrayList(invoiceRepository.getInvoicesByMsid(membership.getMsId()));
        this.membershipIdDTOS = FXCollections.observableArrayList(param -> new Observable[]{param.isRenewProperty()});
        this.membershipIdDTOS.addAll(membershipIdRepository.getIds(membership.getMsId()));
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

    public ObservableList<MembershipIdDTO> getMembershipIdDTOS() {
        return membershipIdDTOS;
    }

    public void setMembershipIdDTOS(ObservableList<MembershipIdDTO> membershipIdDTOS) {
        this.membershipIdDTOS = membershipIdDTOS;
    }

    public ObservableList<BoatDTO> getBoats() {
        return boats;
    }

    public void setBoats(ObservableList<BoatDTO> boats) {
        this.boats = boats;
    }

    public MembershipIdRepository getMembershipIdRepository() {
        return membershipIdRepository;
    }

    public void setMembershipIdRepository(MembershipIdRepository membershipIdRepository) {
        this.membershipIdRepository = membershipIdRepository;
    }

    public ObservableList<MemoDTO> getMemos() {
        return memos;
    }

    public void setMemos(ObservableList<MemoDTO> memos) {
        this.memos = memos;
    }
}
