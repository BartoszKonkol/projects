package javax.jao.registry;

public interface IRegistry <K extends IKey, V extends IValue, C>
{
	
	public boolean doCreateKey(final K key) throws Exception;
	public boolean setValue(final V value, final C content) throws Exception;
	public C getValue(final V value) throws Exception;
	public boolean doDeleteKey(final K key) throws Exception;
	public boolean doDeleteValue(final V value) throws Exception;
	public boolean giveEmptyKey(final K key) throws Exception;
	public boolean giveEmptyValue(final V value) throws Exception;
	
}
