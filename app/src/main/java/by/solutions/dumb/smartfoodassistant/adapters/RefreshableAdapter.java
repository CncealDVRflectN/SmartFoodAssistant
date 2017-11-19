package by.solutions.dumb.smartfoodassistant.adapters;

import java.util.List;

import by.solutions.dumb.smartfoodassistant.containers.Container;

public interface RefreshableAdapter {
    void refreshData(List<Container> filteredData);
}
