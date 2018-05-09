package hu.javalife.heroesofempires.hero.rest;

import hu.javalife.heroesofempires.hero.datamodel.Hero;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * @author krisztian
 */
@ApiModel(value = "Hero pager", description = "Model for paging hero")
public class PagerViewModel {
    private long number;
    private long index;
    private long count;
    private List<Hero> data;

    public PagerViewModel() {
    }

    public PagerViewModel(long number, long index, long count, List<Hero> data) {
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
    public List<Hero> getData() {
        return data;
    }

    public void setData(List<Hero> data) {
        this.data = data;
    }
    
    
}
