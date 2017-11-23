package by.solutions.dumb.smartfoodassistant.util;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import by.solutions.dumb.smartfoodassistant.adapters.RefreshableAdapter;
import by.solutions.dumb.smartfoodassistant.containers.Container;

public class ContainerListFilter extends Filter {

    //region Variables

    private List<Container> allData;
    private List<Container> filteredData;
    private RefreshableAdapter adapter;

    //endregion


    //region Constructors

    public ContainerListFilter(List<Container> allData, List<Container> filteredData, RefreshableAdapter adapter) {
        this.allData = allData;
        this.filteredData = filteredData;
        this.adapter = adapter;
    }

    //endregion


    //region Filter methods

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        final List<Container> list = allData;
        final List<Container> resultList;
        String filterString = constraint.toString().toLowerCase();
        FilterResults results = new FilterResults();
        Container filterableContainer;
        int count = list.size();

        resultList = new ArrayList<>(count);


        for (int i = 0; i < count; i++) {
            filterableContainer = list.get(i);
            if (filterableContainer.getName().toLowerCase().contains(filterString)) {
                resultList.add(filterableContainer);
            }
        }

        results.values = resultList;
        results.count = resultList.size();

        return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        filteredData = (ArrayList<Container>) results.values;
        adapter.refreshData(filteredData);
    }

    //endregion

}
