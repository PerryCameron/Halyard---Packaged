package com.ecsail.structures;

public class WaitListDTO {
	private int ms_id;
	private boolean slipWait;
	private boolean kayakWait;
	private boolean shedWait;
	private boolean wantToSublease;
	private boolean wantsRelease;
	private boolean wantSlipChange;
	
	public WaitListDTO(int ms_id, boolean slipWait, boolean kayakWait, boolean shedWait, boolean wantToSublease,
					   boolean wantsRelease, boolean wantSlipChange) {
		this.ms_id = ms_id;
		this.slipWait = slipWait;
		this.kayakWait = kayakWait;
		this.shedWait = shedWait;
		this.wantToSublease = wantToSublease;
		this.wantsRelease = wantsRelease;
		this.wantSlipChange = wantSlipChange;
	}

	public int getMs_id() {
		return ms_id;
	}

	public void setMs_id(int ms_id) {
		this.ms_id = ms_id;
	}

	public boolean isSlipWait() {
		return slipWait;
	}

	public void setSlipWait(boolean slipWait) {
		this.slipWait = slipWait;
	}

	public boolean isKayakWait() {
		return kayakWait;
	}

	public void setKayakWait(boolean kayakWait) {
		this.kayakWait = kayakWait;
	}

	public boolean isShedWait() {
		return shedWait;
	}

	public void setShedWait(boolean shedWait) {
		this.shedWait = shedWait;
	}

	public boolean isWantToSublease() {
		return wantToSublease;
	}

	public void setWantToSublease(boolean wantToSublease) {
		this.wantToSublease = wantToSublease;
	}

	public boolean isWantsRelease() {
		return wantsRelease;
	}

	public void setWantsRelease(boolean wantsRelease) {
		this.wantsRelease = wantsRelease;
	}

	public boolean isWantSlipChange() {
		return wantSlipChange;
	}

	public void setWantSlipChange(boolean wantSlipChange) {
		this.wantSlipChange = wantSlipChange;
	}
}
