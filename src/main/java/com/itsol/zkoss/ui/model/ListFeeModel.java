/**
 * 
 */
package com.itsol.zkoss.ui.model;

import java.util.Arrays;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import com.itsol.couchbase.service.FeeService;
import com.itsol.springmvc.model.Fee;

/**
 * @author huylv
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ListFeeModel extends AbstractModel {
	@WireVariable
    private FeeService feeService;
	private Fee selectedItem;
	
	@Init
    public void init() {    // Initialize
		selectedItem = feeService.getFee();
    }
	
	/**
	 * @return the selectedItem
	 */
	public Fee getSelectedItem() {
		return selectedItem;
	}

	/**
	 * @param selectedItem the selectedItem to set
	 */
	public void setSelectedItem(Fee selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	public List<Fee> getFees() {
		return new ListModelList<Fee>(Arrays.asList(selectedItem));
	}

	@AfterCompose
    public void initSetup(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
    }
	
    @Command
    @NotifyChange({"selectedItem","fees"})
    public void editFee(@BindingParam("fee") Fee fee) {
    	feeService.updateFee(fee);
    	Messagebox.show("Fee is updated!");
    }
}
