package com.ecsail;

import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.MemoDTO;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.Comparator;

public class Note {
	private ObservableList<MemoDTO> memos;
	private int msid;
	
	public Note(ObservableList<MemoDTO> memos, int m) {
		super();
		this.memos = memos;
		this.msid = m;
		//Collections.sort(memos, (p1,p2) -> p1.getMemo_date().compareTo(p2.getMemo_date()));
		Collections.sort(memos, Comparator.comparing(MemoDTO::getMemo_date).reversed());
	}
	
	public Note() { // overload
		super();
	}

	public int addMemoAndReturnId(String note, String date, int invoice_id, String category) {
		//String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
		int memo_id = SqlSelect.getNextAvailablePrimaryKey("memo","memo_id");
		MemoDTO memo = new MemoDTO(memo_id,msid,date,note,invoice_id,category);
		memos.add(memo); // add in observable list
		addMemo(memo); // add in SQL
		Collections.sort(memos, Comparator.comparing(MemoDTO::getMemo_id).reversed());
		return memo_id;
	}
	
	public void addMemo(MemoDTO memo) {
        SqlInsert.addMemo(memo);
	}
	
	public void updateMemo(int memo_id, String field, String attribute)  {
		SqlUpdate.updateMemo(memo_id, field, attribute);
	}
	
	public void removeMemo(int index) {
		SqlDelete.deleteMemo(memos.get(index));
	}

	public ObservableList<MemoDTO> getMemos() {
		return memos;
	}

	public void setMemos(ObservableList<MemoDTO> memos) {
		this.memos = memos;
	}

	public int getMsid() {
		return msid;
	}

	public void setMsid(int msid) {
		this.msid = msid;
	}
}
