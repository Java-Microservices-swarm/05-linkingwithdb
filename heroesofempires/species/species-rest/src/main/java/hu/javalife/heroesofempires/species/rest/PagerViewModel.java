package hu.javalife.heroesofempires.species.rest;

import hu.javalife.heroesofempires.species.daomodel.Species;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * @author krisztian
 */
@ApiModel(value = "Species pager", description = "Model for paging species")
public class PagerViewModel {
    private long number;
    private long index;
    private long count;
    private List<Species> data;

    public PagerViewModel() {
    }

    public PagerViewModel(long number, long index, long count, List<Species> data) {
        this.number = number;
        this.index = index;
        this.count = count;
        this.data = data;
    }
@ApiModelProperty(value = "Number of all hero", dataType = "long")
    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

@ApiModelProperty(value = "List start index", dataType = "long")
    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }
    
@ApiModelProperty(value = "Maximum number of sub-list", dataType = "long")
    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

@ApiModelProperty(value = "Sub-list", dataType = "long")
    public List<Species> getData() {
        return data;
    }

    public void setData(List<Species> data) {
        this.data = data;
    }
    
    
}
