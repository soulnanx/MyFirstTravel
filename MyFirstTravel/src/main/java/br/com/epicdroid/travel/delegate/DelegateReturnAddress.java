package br.com.epicdroid.travel.delegate;

import br.com.epicdroid.travel.utils.AddressDTO;

public interface DelegateReturnAddress {
    void onReturn(AddressDTO address);
}
