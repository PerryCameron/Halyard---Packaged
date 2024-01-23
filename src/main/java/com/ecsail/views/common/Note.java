package com.ecsail.views.common;

import com.ecsail.dto.MemoDTO;
import com.ecsail.repository.implementations.MemoRepositoryImpl;
import com.ecsail.repository.interfaces.MemoRepository;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.Comparator;

public class Note {
	private ObservableList<MemoDTO> memos;
	private int msId;
	private final MemoRepository memoRepository = new MemoRepositoryImpl();
	
	public Note(ObservableList<MemoDTO> memos, int m) {
		super();
		this.memos = memos;
		this.msId = m;
		Collections.sort(memos, Comparator.comparing(MemoDTO::getMemo_date).reversed());
	}
	
	public Note() { // overload
		super();
	}

	public int addMemoAndReturnId(MemoDTO memoDTOIn) {
		System.out.println(memoDTOIn);
		MemoDTO memoDTO = memoRepository.insertMemo(memoDTOIn);
		memos.add(memoDTO); // add in observable list
		Collections.sort(memos, Comparator.comparing(MemoDTO::getMemo_id).reversed());
		return memoDTO.getMemo_id();
	}
	
	public void addMemo(MemoDTO memo) {
        memoRepository.insertMemo(memo);
	}
	
	public void updateMemo(int memo_id, String field, String attribute)  {
		memoRepository.updateMemo(memo_id, field, attribute);
	}
	
	public void removeMemo(int index) {
		memoRepository.deleteMemo(memos.get(index));
	}

	public ObservableList<MemoDTO> getMemos() {
		return memos;
	}

	public void setMemos(ObservableList<MemoDTO> memos) {
		this.memos = memos;
	}

	public int getMsId() {
		return msId;
	}

	public void setMsId(int msId) {
		this.msId = msId;
	}
}
