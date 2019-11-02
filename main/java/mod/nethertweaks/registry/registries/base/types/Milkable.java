package mod.nethertweaks.registry.registries.base.types;

public class Milkable
{
	public Milkable(final String entityOnTop, final String result, final int amount, final int coolDown) {
		super();
		this.entityOnTop = entityOnTop;
		this.result = result;
		this.amount = amount;
		this.coolDown = coolDown;
	}
	public String getEntityOnTop() {
		return entityOnTop;
	}
	public void setEntityOnTop(final String entityOnTop) {
		this.entityOnTop = entityOnTop;
	}
	public String getResult() {
		return result;
	}
	public void setResult(final String result) {
		this.result = result;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(final int amount) {
		this.amount = amount;
	}
	public int getCoolDown() {
		return coolDown;
	}
	public void setCoolDown(final int coolDown) {
		this.coolDown = coolDown;
	}
	@Override
	public String toString() {
		return "Milkable [entityOnTop=" + entityOnTop + ", result=" + result + ", amount=" + amount + ", coolDown="
				+ coolDown + "]";
	}
	String entityOnTop;
	String result;
	int amount;
	int coolDown;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + coolDown;
		result = prime * result + (entityOnTop == null ? 0 : entityOnTop.hashCode());
		result = prime * result + (this.result == null ? 0 : this.result.hashCode());
		return result;
	}
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Milkable other = (Milkable) obj;
		if (amount != other.amount)
			return false;
		if (coolDown != other.coolDown)
			return false;
		if (entityOnTop == null) {
			if (other.entityOnTop != null)
				return false;
		} else if (!entityOnTop.equals(other.entityOnTop))
			return false;
		if (result == null)
			return other.result == null;
		else return result.equals(other.result);
    }
}
