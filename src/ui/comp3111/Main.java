package ui.comp3111;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.controlsfx.control.CheckListView;

import core.comp3111.AnimatedLineChart;
import core.comp3111.BarChartP;
import core.comp3111.Chart;
import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;
import core.comp3111.LineChartP;
import core.comp3111.SampleDataGenerator;
import core.comp3111.DataFilterManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The Main class of this GUI application
 * 
 * @author cspeter
 *
 */
public class Main extends Application {

	// Attribute: DataTable
	// In this sample application, a single data table is provided
	// You need to extend it to handle multiple data tables
	// Hint: Use java.util.List interface and its implementation classes (e.g.
	// java.util.ArrayList)
	private DataTable sampleDataTable = null;
	private ListView<String> dataTablesName = new ListView<String>();

	private List<DataTable> dataTables = new ArrayList<DataTable>();

	private ListView<String> datasetslist = new ListView<String>();

	private ListView<String> plotChartTypeList = new ListView<String>();
	private int graphSelectedIndex = 0;
	private ObservableList<String> chartTypesName = FXCollections.observableArrayList("Line Chart", "Bar Chart",
			"Animated Line Chart");

	private int datasetsSelectedIndex = 0;
	private ObservableList<String> datasetsname = FXCollections.observableArrayList();

	private int chartslistSelectedIndex = 0;
	private List<Chart> charts = new ArrayList<Chart>();
	private ListView<String> chartslist = new ListView<String>();
	private ObservableList<String> chartsname = FXCollections.observableArrayList();

	private List<DataColumn> dataColumns = new ArrayList<DataColumn>();
	private ListView<String> dataColumnslistX = new ListView<String>();
	private ListView<String> dataColumnslistY = new ListView<String>();
	private ObservableList<String> dataColumnsName = FXCollections.observableArrayList();

	private ChoiceBox<String> cbfornumfield = new ChoiceBox<String>();
	private ChoiceBox<String> cbforoperator = new ChoiceBox<String>();
	private ChoiceBox<String> cbfortextfield = new ChoiceBox<String>();
	private ObservableList<String> textFieldName = FXCollections.observableArrayList();
	private ObservableList<String> numFieldName = FXCollections.observableArrayList();
	private ObservableList<String> uniqueTextinColSelected = FXCollections.observableArrayList();

	private CheckListView<String> checkListView = new CheckListView<>();

	private boolean showChartLock = false;

	// for animation
	private Timeline timeline;
	// Just for testing puropose

	// ObservableList<String> items =FXCollections.observableArrayList ("Single",
	// "Double", "Suite", "Family App");
	// list.setItems(items);
	// Attributes: Scene and Stage

	private static final int SCENE_NUM = 6;
	private static final int SCENE_MAIN_SCREEN = 0;
	private static final int SCENE_LINE_CHART = 1;;
	private static final int SCENE_DATA_FILTER = 2;
	private static final int PLOT_GRAPH = 3;
	private static final int SCENE_AXIS_SELECT = 4;
	private static final int SCENE_CHART = 5;
	private static final String[] SCENE_TITLES = { "COMP3111 Chart - Pepsi", "Sample Line Chart Screen",
			"Data filtering and transformation", "Plot graph with selected dataset", "Select axis", "Chart view" };
	private Stage stage = null;
	private Scene[] scenes = null;

	// To keep this application more structural,
	// The following UI components are used to keep references after invoking
	// createScene()
	// Screen 1: paneMainScreen
	private Button dataSetPreviewButton, dataFilterReplaceDatasetButton, dataFilterSaveAsNewDatasetButton,
			dataFilterBackMain, btSampleLineChartData, btSampleLineChartDataV2, btSampleLineChart, importButton,
			exportButton, savingButton, loadingButton, dataFilteringAndTransformationButton, plotGraphButton,
			showChartButton;
	private Label lbSampleDataTable, lbMainScreenTitle, showDataLabel, dFS_Warninglb, datasetlabel, chartlabel,
			numberfiltertitlelb, textfiltertitlelb;

	private CheckBox numberfiltercb, textfiltercb;

	private TextField numberfield;
	// private ListView<DataTable> datasetListView;

	// private ListView<> ChartListView;

	// Screen 2: paneSampleLineChartScreen
	private LineChart<Number, Number> lineChart = null;
	private NumberAxis xAxis = null;
	private NumberAxis yAxis = null;
	private Button btLineChartBackMain = null;

	// Screen 3 :panePlotGraphScreen
	private Button plotChartBackMainBtn, selectChartTypeBtn;
	private Label chartSelectionDataLabel, chartInvalidDataset;

	// Screen 4: paneAxisScreen
	private Button btAxisBack, selectAxisBtn;
	private Label axisSelectionLabel, invalidAxis, xAxisLabel, yAxisLabel;
	private ObservableList<String> selectedColumnsX = null;
	private ObservableList<String> selectedColumnsY = null;

	private ObservableList<String> lineChartDropDownX = FXCollections.observableArrayList();
	private ObservableList<String> lineChartDropDownY = FXCollections.observableArrayList();
	private ComboBox<String> comboBoxX, comboBoxY;
	private String xSelected, ySelected;

	// Screen 5 : paneChartScreen
	private Chart chart = null;
	private Button btChartBackMain, saveChartBtn, btChartBack;
	private LineChart<Number, Number> lineChartFinal = null;
	private BarChart<String, Number> barChartFinal = null;
	private LineChart<Number, Number> animatedLineChartFinal = null;
	private NumberAxis xAxisNum = null;
	private NumberAxis yAxisNum = null;
	private CategoryAxis xAxisTxt = null;
	private double minVal, maxVal, speed;

	/**
	 * create all scenes in this application
	 */
	private void initScenes() {
		scenes = new Scene[SCENE_NUM];
		scenes[SCENE_MAIN_SCREEN] = new Scene(paneMainScreen(), 850, 850);
		scenes[SCENE_LINE_CHART] = new Scene(paneLineChartScreen(), 800, 600);

		scenes[SCENE_DATA_FILTER] = new Scene(paneDataFilterScreen(), 800, 600);
		scenes[PLOT_GRAPH] = new Scene(panePlotGraphScreen(), 800, 600);

		// scenes[SCENE_LINE_CHART] = new Scene(paneLineChartScreen(), 700, 600);
		for (Scene s : scenes) {
			if (s != null)
				// Assumption: all scenes share the same stylesheet
				s.getStylesheets().add("Main.css");
		}

	}

	/**
	 * update the dataset list and chart list on the main screen
	 */
	private void updateDatasetsListandChartList() {

		datasetsname.clear();

		for (int i = 0; i < dataTables.size(); i++) {

			datasetsname.add("Dataset " + String.valueOf(i + 1));
		}

		chartsname.clear();
		for (int i = 0; i < charts.size(); ++i) {

			chartsname.add(charts.get(i).getTitle());
		}

		// highlight and select the newly added dataset by default
		datasetslist.getSelectionModel().select(dataTables.size() - 1);
		chartslist.getSelectionModel().select(charts.size() - 1);

	}

	/**
	 * Do filtering based on user selected preference on sampleDataTable
	 * @return false if input is not complete
	 * 
	 */
	private boolean filterSampleDataSet() {

		// Check if one of them is ticked

		if ((!textfiltercb.isSelected()) && (!numberfiltercb.isSelected())) {
			return false;
		}

		if (numberfiltercb.isSelected()) {
			// check if all field is chosen

			if ((cbfornumfield.getValue() == null)||(cbforoperator.getValue() == null)||(numberfield.getText() == null || numberfield.getText().trim().isEmpty())) {
				return false;
			}
			Float f = Float.parseFloat(numberfield.getText());
			String Operatorusing = cbforoperator.getValue(); 
			String columnNamechosen = cbfornumfield.getValue();
			try {
				sampleDataTable = DataFilterManager.NumberFilterSet(columnNamechosen, Operatorusing, f, sampleDataTable);
			} catch (DataTableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// perform number filter function on sampleDataTable
		}
		if (textfiltercb.isSelected()) {
			if (cbfortextfield.getValue() == null) {
				return false;
			}
			String textChosenInTextField = cbfortextfield.getValue();
			List<String> checkeditems = checkListView.getCheckModel().getCheckedItems();
			try {
				sampleDataTable = DataFilterManager.TextFilterSet(textChosenInTextField, checkeditems, sampleDataTable);
			} catch (DataTableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// perform text filter function
		}
		return true;
	}

	/**
	 * update the column list on the axis selection screen
	 */
	private void updateAxisList() {
		switch (graphSelectedIndex) {
		// bar chart
		case 1:
			dataColumnsName.clear();

			DataTable tempDataTable = dataTables.get(datasetsSelectedIndex);
			dataColumns = tempDataTable.getAllColValue();
			List<String> tempColName = tempDataTable.getAllColName();

			int numCol = tempDataTable.getNumCol();

			for (int i = 0; i < numCol; ++i) {
				dataColumnsName.add(tempColName.get(i));
			}

			dataColumnslistX.setItems(dataColumnsName);
			dataColumnslistY.setItems(dataColumnsName);

			dataColumnslistX.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			dataColumnslistY.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

			// select the first column on the axis selection list by default
			dataColumnslistX.getSelectionModel().select(0);
			dataColumnslistY.getSelectionModel().select(0);
			break;

		case 0:
		case 2:
			List<String> xColumnsList = dataTables.get(datasetsSelectedIndex).getAllColName();
			List<String> yColumnsList = dataTables.get(datasetsSelectedIndex).getAllColName();

			for (int i = 0; i < xColumnsList.size(); ++i)
				comboBoxX.getItems().add(xColumnsList.get(i));

			for (int i = 0; i < yColumnsList.size(); ++i)
				comboBoxY.getItems().add(yColumnsList.get(i));
			break;

		}

	}

	/**
	 * updates the chart screen after the columns for axis have been selected
	 */
	private void updateSceneChart(Chart chart) {
		scenes[SCENE_CHART] = new Scene(paneChartScreen(chart), 800, 600);
		initChartScreenHandlers();
	}

	/**
	 * updates the chart screen after the chart type has been selected
	 */
	private void updateSceneAxisSelect() {
		scenes[SCENE_AXIS_SELECT] = new Scene(paneAxisScreen(), 800, 600);
		scenes[SCENE_AXIS_SELECT].getStylesheets().add("Main.css");
		initAxisScreenHandlers();
	}

	/**
	 * This method will be invoked after createScenes(). In this stage, all UI
	 * components will be created with a non-NULL references for the UI components
	 * that requires interaction (e.g. button click, or others).
	 */
	private void initEventHandlers() {
		initMainScreenHandlers();
		initLineChartScreenHandlers();
		initDataFilterScreenHandlers();
		initGraphTypeSelectionScreenHandlers();

	}

	/**
	 * Initialize event handlers of the line chart screen
	 */
	private void initLineChartScreenHandlers() {

		// click handler
		btLineChartBackMain.setOnAction(e -> {
			putSceneOnStage(SCENE_MAIN_SCREEN);
		});
	}

	private void initDataFilterScreenHandlers() {

		// click handler
		dataFilterBackMain.setOnAction(e -> {
			putSceneOnStage(SCENE_MAIN_SCREEN);

			updateDatasetsListandChartList();
		});

		numberfield.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				numberfield.setText(oldValue);

			}
		});

		// cbfortextfield.getSelectionModel().selectedItemProperty().addListener(
		// (ObservableValue<? extends String> observable, String oldValue, String
		// newValue) -> System.out.println(newValue) );
		cbfortextfield.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
//				// System.out.println(cbfortextfield.getItems().get((Integer) number2));
//				System.out.println((Integer) number2);

				DataColumn b = sampleDataTable.getCol(cbfortextfield.getItems().get((Integer) number2));

				List<String> a = new ArrayList<>();
				String[] xDouble = (String[]) b.getData();
				for (int i = 1; i < xDouble.length; ++i) {

					if (!a.contains(xDouble[i])) {
						a.add(xDouble[i]);
					}
				}
//
//				System.out.println("The size of a is " + xDouble.length);

				uniqueTextinColSelected = FXCollections.observableArrayList(a);
				checkListView.setItems(uniqueTextinColSelected);

				checkListView.getCheckModel().checkAll();
			}
		});
		dataFilterReplaceDatasetButton.setOnAction(e -> {
			// TODO save the modify dataset to same dataset sampleDataTable

			if (filterSampleDataSet()) {
				dataTables.remove(datasetsSelectedIndex);
				dataTables.add(datasetsSelectedIndex, sampleDataTable);
				updateDatasetsListandChartList();

				putSceneOnStage(SCENE_MAIN_SCREEN);
			} else {

				dFS_Warninglb.setText(
						"Reminder!! 1. You have to check which filtering method you want to use! 2. Remember to select what you want to filter or we cannot perform filtering for you");

				// show error message
			}
			// dataTables.listIterator(datasetsSelectedIndex);

		});

		dataFilterSaveAsNewDatasetButton.setOnAction(e -> {

			if (filterSampleDataSet()) {
				dataTables.add(sampleDataTable);

				updateDatasetsListandChartList();

				putSceneOnStage(SCENE_MAIN_SCREEN);
			} else {
				// show error message
				dFS_Warninglb.setText(
						"Reminder!! 1. You have to check which filtering method you want to use! 2. Remember to select what you want to filter or we cannot perform filtering for you");

			}

		});
	}

	/**
	 * Initialize event handlers of the plot graph type selection screen
	 * 
	 */

	private void initGraphTypeSelectionScreenHandlers() {
		// click handler
		plotChartBackMainBtn.setOnAction(e -> {
			putSceneOnStage(SCENE_MAIN_SCREEN);
			chartInvalidDataset.setText("");
		});

		selectChartTypeBtn.setOnAction(e -> {
			graphSelectedIndex = plotChartTypeList.getFocusModel().getFocusedIndex();
//			System.out.println(graphSelectedIndex);
			// data requirement check
			switch (graphSelectedIndex) {
			case 0:
				// Line Chart
				chart = new LineChartP(dataTables.get(datasetsSelectedIndex));

				if (chart.dataRequirementValidation()) {
					updateSceneAxisSelect();
					putSceneOnStage(SCENE_AXIS_SELECT);
					chartInvalidDataset.setText("");
				} else
					chartInvalidDataset.setText("Line chart requires at least 2 numeric columns!");
				break;

			case 1:
				// Bar Chart
				chart = new BarChartP(dataTables.get(datasetsSelectedIndex));

				if (chart.dataRequirementValidation()) {
					updateSceneAxisSelect();
					putSceneOnStage(SCENE_AXIS_SELECT);
					chartInvalidDataset.setText("");
				} else
					chartInvalidDataset.setText("Bar chart requires at least 1 text column and 1 numeric column!");
				break;

			case 2:
				// Animated Line Chart
				chart = new AnimatedLineChart(dataTables.get(datasetsSelectedIndex));
				if (chart.dataRequirementValidation()) {
					updateSceneAxisSelect();
					putSceneOnStage(SCENE_AXIS_SELECT);
					chartInvalidDataset.setText("");
				} else
					chartInvalidDataset.setText("Animated line chart requires at least 2 numeric columns!");
				break;

			}

			updateAxisList();

		});
	}

	/**
	 * Initialize event handlers of the Axis selection screen
	 * 
	 */
	private void initAxisScreenHandlers() {

		// click handler
		btAxisBack.setOnAction(e -> {

			putSceneOnStage(PLOT_GRAPH);
			invalidAxis.setText("");
		});

		selectAxisBtn.setOnAction(e -> {

			DataTable selectedDT = dataTables.get(datasetsSelectedIndex);
			DataTable Xcol = new DataTable();
			DataTable Ycol = new DataTable();
			// String selectedDTTitle = selectedDT.getDataTableName();
			String selectedDTTitle = "";
			// put the selected columns into datatable Xcol and Ycol
			switch (graphSelectedIndex) {
			case 0:
			case 2:
				if (comboBoxX.getValue() != null) {
					xSelected = comboBoxX.getValue().toString();

					try {
						Xcol.addCol(xSelected, dataTables.get(datasetsSelectedIndex).getCol(xSelected));
					} catch (DataTableException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

				}

				if (comboBoxY.getValue() != null) {
					ySelected = comboBoxY.getValue().toString();
					try {
						Ycol.addCol(ySelected, dataTables.get(datasetsSelectedIndex).getCol(ySelected));
					} catch (DataTableException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
				selectedDTTitle = xSelected + "v" + ySelected;

				break;

			case 1:
				selectedColumnsX = dataColumnslistX.getSelectionModel().getSelectedItems();
				selectedColumnsY = dataColumnslistY.getSelectionModel().getSelectedItems();

				for (int i = 0; i < selectedColumnsX.size(); ++i)
					try {
						Xcol.addCol(selectedColumnsX.get(i), selectedDT.getCol(selectedColumnsX.get(i)));
					} catch (DataTableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				for (int i = 0; i < selectedColumnsY.size(); ++i)
					try {
						Ycol.addCol(selectedColumnsY.get(i), selectedDT.getCol(selectedColumnsY.get(i)));
					} catch (DataTableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				selectedDTTitle = selectedColumnsX.get(0);
				break;

			}

			// Add chart to charts list
			switch (graphSelectedIndex) {
			// Line Chart
			case 0:
				// no text column & at least 1 numeric column for each X and Y
				if (Xcol.textCountDT() == 0 && Ycol.textCountDT() == 0 && Xcol.numCountDT() == 1
						&& Ycol.numCountDT() == 1) {
					LineChartP lineChart = new LineChartP(selectedDT, Xcol, Ycol, selectedDTTitle);
					charts.add(lineChart);


					updateSceneChart(lineChart);
					populateToLineChart(Xcol, Ycol, selectedDTTitle);

					putSceneOnStage(SCENE_CHART);

					invalidAxis.setText("");
				} else
					invalidAxis.setText(
							"Line chart requires no text column and one numeric column for each X and Y axis!");

				break;

			// Bar Chart
			case 1:
				// 1 text column on X axis and multiple numeric columns on Y axis
				if (Xcol.textCountDT() == 1 && Ycol.textCountDT() == 0 && Xcol.numCountDT() == 0
						&& Ycol.numCountDT() != 0) {

					BarChartP barChart = new BarChartP(selectedDT, Xcol, Ycol, selectedDTTitle);

					charts.add(barChart);

					updateSceneChart(barChart);
					populateToBarChart(Xcol, Ycol, selectedDTTitle);

					putSceneOnStage(SCENE_CHART);
					invalidAxis.setText("");
				} else
					invalidAxis.setText(
							"Bar chart requires 1 text column on X axis and at least one numeric column on Y axis!");
				break;

			// Animated Line Chart
			case 2:
				// only 1 numeric column on each X and Y axis
				if (Xcol.textCountDT() == 0 && Ycol.textCountDT() == 0 && Xcol.numCountDT() == 1
						&& Ycol.numCountDT() == 1) {

					AnimatedLineChart animatedLineChart = new AnimatedLineChart(selectedDT, Xcol, Ycol,
							selectedDTTitle);
					charts.add(animatedLineChart);

					updateSceneChart(animatedLineChart);

					// animation using timeline
					timeline = new Timeline();
					timeline.setCycleCount(Timeline.INDEFINITE);
					timeline.setAutoReverse(true);
					timeline.getKeyFrames()
							.add(new KeyFrame(Duration.millis(100), (ActionEvent actionEvent) -> plotAnimatedChart()));

					populateToAnimatedLineChart(Xcol, Ycol, selectedDTTitle);
					putSceneOnStage(SCENE_CHART);

					timeline.play();
					invalidAxis.setText("");

				} else
					invalidAxis.setText(
							"Animated line chart requires no text column and one numeric column for each X and Y axis!");

				break;

			}
		});

	}

	/**
	 * Initialize event handlers of the Chart screen
	 */
	private void initChartScreenHandlers() {

		// click handler
		btChartBack.setOnAction(e -> {
			if (!showChartLock) {
				charts.remove(charts.size() - 1);
				putSceneOnStage(SCENE_AXIS_SELECT);
			} else {
				putSceneOnStage(SCENE_MAIN_SCREEN);
				showChartLock = false;
			}

			if (timeline != null)
				timeline.stop();
		});

		btChartBackMain.setOnAction(e -> {
			if (!showChartLock)
				charts.remove(charts.size() - 1);
			else
				showChartLock = false;
			putSceneOnStage(SCENE_MAIN_SCREEN);
			if (timeline != null)
				timeline.stop();

		});

		saveChartBtn.setOnAction(e -> {
			// delete chart if entered through show chart button on the main screen
			if (showChartLock) {
				charts.remove(chartslistSelectedIndex);
				showChartLock = false;
			}

			putSceneOnStage(SCENE_MAIN_SCREEN);

			if (timeline != null)
				timeline.stop();

		});

	}

	/**
	 * sort an Number array into double array. Used for finding the min and max
	 * boundary for X axis
	 * 
	 * @param inputArr
	 * @return double array sorted in ascending order
	 */
	private double[] ascendingSort(Number[] inputArr) {
		// sort xValues in ascending order. For min and max value of the xAxis
		double[] xDouble = new double[inputArr.length - 1];
		for (int i = 1; i < inputArr.length; ++i) {
			xDouble[i - 1] = (double) inputArr[i];
		}

		for (int i = 0; i < xDouble.length; ++i) {
			double min = xDouble[i];
			double temp;
			for (int j = i + 1; j < xDouble.length; ++j) {
				if (min > xDouble[j]) {
					min = xDouble[j];

					temp = xDouble[i];
					xDouble[i] = xDouble[j];
					xDouble[j] = temp;
				}
			}
		}

		return xDouble;
	}

	/**
	 * change upper and lower bound of Xaxis dynamically to animate the line chart
	 */
	private void plotAnimatedChart() {
		// double speed = xAxisNum.getTickUnit()*2;

		double axisLowerBound = xAxisNum.getLowerBound() + speed;
		double axisUpperBound = xAxisNum.getUpperBound() + speed;
		if (axisUpperBound > maxVal) {
			axisLowerBound = minVal;
			axisUpperBound = minVal + ((maxVal - minVal) / 2);
		}

		// xAxisNum.setTickUnit(speed/4);
		xAxisNum.setLowerBound(axisLowerBound);
		xAxisNum.setUpperBound(axisUpperBound);
	}

	/**
	 * populate selected datacolumns into bar chart view
	 * 
	 * @param X
	 * @param Y
	 * @param title
	 */
	private void populateToBarChart(DataTable X, DataTable Y, String title) {
		List<String> xColumnsList = X.getAllColName();
		List<String> yColumnsList = Y.getAllColName();

		String xName = xColumnsList.get(0);
		String[] yNames = new String[yColumnsList.size()];

		for (int i = 0; i < yColumnsList.size(); ++i)
			yNames[i] = yColumnsList.get(i);

		if (X != null && Y != null) {
			barChartFinal.setTitle(xName + " bar chart");
			xAxisTxt.setLabel(xName);

			XYChart.Series<String, Number>[] series = (XYChart.Series<String, Number>[]) new XYChart.Series[yColumnsList.size()];

			for (int i = 0; i < yColumnsList.size(); ++i) {
				series[i] = new XYChart.Series<String, Number>();
				series[i].setName(yNames[i]);
				String[] xValues = (String[]) X.getCol(xName).getData();
				Number[] yValues = (Number[]) Y.getCol(yNames[i]).getData();

				for (int j = 1; j < yValues.length; ++j) {
					series[i].getData().add(new XYChart.Data<String, Number>(xValues[j], yValues[j]));
					//System.out.println(xValues[j]+"    "+yValues[j]);
				}

			}
			barChartFinal.getData().clear();
			for (int i = 0; i < series.length; ++i) {
				barChartFinal.getData().add(series[i]);
			}
				
			for(int i=0; i< series[0].getData().size();++i)
				System.out.println(series[0].getData().get(i));

		}

	}

	/**
	 * populate selected datacolumns into animated line chart view
	 * 
	 * @param X
	 * @param Y
	 * @param title
	 */

	private void populateToAnimatedLineChart(DataTable X, DataTable Y, String title) {
		List<String> xColumnsList = X.getAllColName();
		List<String> yColumnsList = Y.getAllColName();

		String xName = xColumnsList.get(0);
		String yName = yColumnsList.get(0);
		xAxisNum.setAutoRanging(false);
		xAxisNum.setTickLabelsVisible(true);
		xAxisNum.setTickMarkVisible(true);

		DataColumn xCol = X.getCol(xName);
		DataColumn yCol = Y.getCol(yName);

		if (X != null && Y != null) {
			animatedLineChartFinal.setTitle(xName + "v" + yName + " line chart");
			xAxisNum.setLabel(xName);
			yAxisNum.setLabel(yName);

			XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
			series.setName(yName);
			Number[] xValues = (Number[]) xCol.getData();
			Number[] yValues = (Number[]) yCol.getData();

			double[] xDouble = xCol.ascendingSort();

			minVal = xDouble[0];
			maxVal = xDouble[xDouble.length - 1];
			speed = (maxVal - minVal) / 20;
			xAxisNum.setTickUnit(speed);
			xAxisNum.setTickLabelFill(Color.BLACK);

			for (int i = 1; i < xValues.length; ++i) {
				xValues[i] = (Number) xDouble[i - 1];
			}

			for (int i = 1; i < xValues.length; i++) {
				series.getData().add(new XYChart.Data<Number, Number>(xValues[i], yValues[i]));
			}

			animatedLineChartFinal.getData().clear();
			animatedLineChartFinal.getData().add(series);

			// Set initial X axis range

			double axisLowerBound = minVal;
			double axisUpperBound = minVal + ((maxVal - minVal) / 2);
			xAxisNum.setLowerBound(axisLowerBound);
			xAxisNum.setUpperBound(axisUpperBound);

		}

	}

	/**
	 * populate selected datacolumns into line chart view
	 * 
	 * @param X
	 * @param Y
	 * @param title
	 */
	private void populateToLineChart(DataTable X, DataTable Y, String title) {
		List<String> xColumnsList = X.getAllColName();
		List<String> yColumnsList = Y.getAllColName();

		String xName = xColumnsList.get(0);
		String yName = yColumnsList.get(0);

		DataColumn xCol = X.getCol(xName);
		DataColumn yCol = Y.getCol(yName);

		if (X != null && Y != null) {
			lineChartFinal.setTitle(xName + "v" + yName + " line chart");
			xAxisNum.setLabel(xName);
			yAxisNum.setLabel(yName);

			XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
			series.setName(yName);

			Number[] xValues = (Number[]) xCol.getData();
			Number[] yValues = (Number[]) yCol.getData();

			for (int i = 1; i < xValues.length; i++) {
				series.getData().add(new XYChart.Data<Number, Number>(xValues[i], yValues[i]));
			}

			lineChartFinal.getData().clear();
			lineChartFinal.getData().add(series);
		}

	}

	/**
	 * Populate sample data table values to the chart view
	 */
	private void populateSampleDataTableValuesToChart(String seriesName) {

		// Get 2 columns
		DataColumn xCol = sampleDataTable.getCol("X");
		DataColumn yCol = sampleDataTable.getCol("Y");

		// Ensure both columns exist and the type is number
		if (xCol != null && yCol != null && xCol.getTypeName().equals(DataType.TYPE_NUMBER)
				&& yCol.getTypeName().equals(DataType.TYPE_NUMBER)) {

			lineChart.setTitle("Sample Line Chart");
			xAxis.setLabel("X");
			yAxis.setLabel("Y");

			// defining a series
			XYChart.Series series = new XYChart.Series();

			series.setName(seriesName);

			// populating the series with data
			// As we have checked the type, it is safe to downcast to Number[]
			Number[] xValues = (Number[]) xCol.getData();
			Number[] yValues = (Number[]) yCol.getData();

			// In DataTable structure, both length must be the same
			int len = xValues.length;

			for (int i = 0; i < len; i++) {
				series.getData().add(new XYChart.Data(xValues[i], yValues[i]));
			}

			// clear all previous series
			lineChart.getData().clear();

			// add the new series as the only one series for this line chart
			lineChart.getData().add(series);

		}

	}

	/**
	 * Initialize event handlers of the main screen
	 */
	private void initMainScreenHandlers() {

		// click handler
		btSampleLineChartData.setOnAction(e -> {

			// In this example, we invoke SampleDataGenerator to generate sample data
			try {
				sampleDataTable = SampleDataGenerator.generateSampleLineData();
			} catch (DataTableException e1) {
				e1.printStackTrace();
			}
			lbSampleDataTable.setText(String.format("SampleDataTable: %d rows, %d columns", sampleDataTable.getNumRow(),
					sampleDataTable.getNumCol()));

			populateSampleDataTableValuesToChart("Sample 10");

		});

		// click handler
		btSampleLineChartDataV2.setOnAction(e -> {

			// In this example, we invoke SampleDataGenerator to generate sample data
			try {
				sampleDataTable = SampleDataGenerator.generateSampleLineDataV2();
			} catch (DataTableException e1) {
				e1.printStackTrace();
			}
			lbSampleDataTable.setText(String.format("SampleDataTable: %d rows, %d columns", sampleDataTable.getNumRow(),
					sampleDataTable.getNumCol()));

			populateSampleDataTableValuesToChart("Sample 2");

		});

		// click handler
		btSampleLineChart.setOnAction(e -> {

			putSceneOnStage(SCENE_LINE_CHART);

		});

		showChartButton.setOnAction(e -> {
			showChartLock = true;

			chartslistSelectedIndex = chartslist.getSelectionModel().getSelectedIndex();
			chart = charts.get(chartslistSelectedIndex);

			switch (chart.getTypeID()) {
			case 0:
				updateSceneChart(chart);
				populateToLineChart(chart.getDataTable(1), chart.getDataTable(2), chart.getTitle());
				putSceneOnStage(SCENE_CHART);
				break;
			case 1:
				updateSceneChart(chart);
				populateToBarChart(chart.getDataTable(1), chart.getDataTable(2), chart.getTitle());
				putSceneOnStage(SCENE_CHART);
				break;
			case 2:
				updateSceneChart(chart);

				timeline = new Timeline();
				timeline.setCycleCount(Timeline.INDEFINITE);
				timeline.setAutoReverse(true);
				timeline.getKeyFrames()
						.add(new KeyFrame(Duration.millis(100), (ActionEvent actionEvent) -> plotAnimatedChart()));

				populateToAnimatedLineChart(chart.getDataTable(1), chart.getDataTable(2), chart.getTitle());

				putSceneOnStage(SCENE_CHART);
				timeline.play();
				break;

			}

			saveChartBtn.setText("Delete chart");

		});

		importButton.setOnAction(e -> {
			// Will provide a file chooser, return with a dataTable object.
			try {
				// temp will return null if user cancel action in the middle.
				DataTable temp = DataManager.dataImport(stage);
				if (temp != null) {
					dataTables.add(temp);
				}
			} catch (FileNotFoundException | DataTableException e1) {
				e1.printStackTrace();
			}

			updateDatasetsListandChartList();

		});

		exportButton.setOnAction(e -> {
			// Log G: If a chart is selected, should not allow the user to use it!

			datasetsSelectedIndex = datasetslist.getFocusModel().getFocusedIndex();
			if (datasetsSelectedIndex == -1) {
				showDataLabel.setText(String.format("Please select a dataset to export to .csv"));
			} else {
				sampleDataTable = dataTables.get(datasetsSelectedIndex);

				try {
					DataManager.dataExport(sampleDataTable, stage);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				showDataLabel.setText(String.format("Welcome!"));

			}
		});

		savingButton.setOnAction(e -> {
			// Bill Please add your function here
			try {
				// If not both are empty, then we can save as a .pepsi file.
				if (!(dataTables.isEmpty() && charts.isEmpty())) {
					IOManager.fileExport(dataTables, charts, stage);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		});

		loadingButton.setOnAction(e -> {
			// Bill Please add your function here

			try {
				IOManager.fileImport(stage);
			} catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			// Testing required
			// Please put corresponding datatables and charts ArrayList into attribute in
			// this class.
			List<DataTable> inputDataTable = IOManager.getDataTables();
			for (DataTable DataTableObj : inputDataTable) {
				dataTables.add(DataTableObj);
			}
			List<Chart> inputCharts = IOManager.getCharts();
			if (inputCharts != null && !inputCharts.isEmpty()) {
				for (Chart ChartObj : inputCharts) {
					charts.add(ChartObj);
				}
			}

			updateDatasetsListandChartList();

		});

		dataFilteringAndTransformationButton.setOnAction(e -> {
			// Log G Please add your function here

			datasetsSelectedIndex = datasetslist.getFocusModel().getFocusedIndex();
			if (datasetsSelectedIndex == -1) {
				showDataLabel.setText(String.format("Please select a dataset to do filtering and transformation."));

			} else {
				sampleDataTable = dataTables.get(datasetsSelectedIndex);

				numFieldName.clear();
				numFieldName = FXCollections.observableList(sampleDataTable.getAllNumColName());
				cbfornumfield.setItems(numFieldName);

				textFieldName.clear();
				textFieldName = FXCollections.observableList(sampleDataTable.getAllTextColName());
				cbfortextfield.setItems(textFieldName);
				cbfortextfield.getSelectionModel().select(-1);
				// icy
				uniqueTextinColSelected.clear();
				checkListView.setItems(uniqueTextinColSelected);
				
				

				// System.out.println("Size of numFieldName: "+ numFieldName.size());
				putSceneOnStage(SCENE_DATA_FILTER);
				showDataLabel.setText(String.format("Welcome!"));

			}

			updateDatasetsListandChartList();
		});

		dataSetPreviewButton.setOnAction(e -> {

			datasetsSelectedIndex = datasetslist.getFocusModel().getFocusedIndex();

			sampleDataTable = dataTables.get(datasetsSelectedIndex);

			if (datasetsSelectedIndex == -1 || sampleDataTable == null) {
				showDataLabel.setText(String.format("No dataset being selected to be preview"));

			} else {
				sampleDataTable = dataTables.get(datasetsSelectedIndex);
				showDataLabel.setText(String.format("Dataset %d : %d rows, %d columns", datasetsSelectedIndex + 1,
						sampleDataTable.getNumRow(), sampleDataTable.getNumCol()));

			}

			updateDatasetsListandChartList();
		});

		plotGraphButton.setOnAction(e -> {

			datasetsSelectedIndex = datasetslist.getFocusModel().getFocusedIndex();
//			System.out.println(datasetsSelectedIndex);
			if (datasetsSelectedIndex == -1) {
				showDataLabel.setText(String.format("Please select a dataset to plot a graph."));
			} else {
				sampleDataTable = dataTables.get(datasetsSelectedIndex);

				// use the sampleDataTable variable to plot graph

				// Won Please add your function here
				// select Line Chart option by default and put the chart selection scene on
				chartInvalidDataset.setText("");
				plotChartTypeList.getSelectionModel().select(0);
				putSceneOnStage(PLOT_GRAPH);

			}

			updateDatasetsListandChartList();
		});

	}

	/**
	 * Create the line chart screen and layout its UI components
	 * 
	 * @return a Pane component to be displayed on a scene
	 */
	private Pane paneLineChartScreen() {

		xAxis = new NumberAxis();
		yAxis = new NumberAxis();
		lineChart = new LineChart<Number, Number>(xAxis, yAxis);

		btLineChartBackMain = new Button("Back");

		xAxis.setLabel("undefined");
		yAxis.setLabel("undefined");
		lineChart.setTitle("An empty line chart");

		// Layout the UI components
		VBox container = new VBox(20);
		container.getChildren().addAll(lineChart, btLineChartBackMain);
		container.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(container);

		// Apply CSS to style the GUI components
		pane.getStyleClass().add("screen-background");

		return pane;
	}

	private Pane paneDataFilterScreen() {

		dFS_Warninglb = new Label(
				"Choose which kind of filtering do you want to perform! Remember to check the box of the respective method before you press the button!");

		HBox numberfiltertitlegroup = new HBox(20);

		numberfiltertitlelb = new Label(
				"Filtering numeric data : (check this box if you want to perform numeric filtering) ");
		numberfiltercb = new CheckBox();

		numberfiltertitlegroup.setAlignment(Pos.CENTER);
		numberfiltertitlegroup.getChildren().addAll(numberfiltertitlelb, numberfiltercb);

		VBox container = new VBox(20);

		dataFilterReplaceDatasetButton = new Button("Replacing the current dataset");

		// TODO
		// List<String> a = sampleDataTable.getAllColName();
		//
		// if (a == null) {
		// a.add("");
		// }

		cbfornumfield.setItems(numFieldName);

		// FXCollections.observableArrayList("First", "Second", "Third")
		cbfornumfield.setTooltip(new Tooltip("Select the numeric field you want to filter"));

		cbforoperator = new ChoiceBox<String>(FXCollections.observableArrayList(">", "=", "<"));

		cbforoperator.setTooltip(new Tooltip("Select the operator you want to filter on the field"));

		numberfield = new TextField();

		dataFilterSaveAsNewDatasetButton = new Button("Save as new dataset");
		dataFilterBackMain = new Button("Back");

		HBox numberfiltergroup = new HBox(20);

		numberfiltergroup.setAlignment(Pos.CENTER);
		numberfiltergroup.getChildren().addAll(cbfornumfield, cbforoperator, numberfield);

		HBox textfiltertitlegroup = new HBox(20);

		textfiltertitlelb = new Label(
				"Filtering text type data : (check this box if you want to perform text filtering) (only one field can be perform to at each time)");
		textfiltercb = new CheckBox();
		textfiltertitlegroup.setAlignment(Pos.CENTER);

		cbfornumfield.setItems(textFieldName);
		cbfortextfield.setTooltip(new Tooltip("Select the text field you want to filter"));

		textfiltertitlegroup.getChildren().addAll(textfiltertitlelb, textfiltercb);

		// create the data to show in the CheckListView
		// ObservableList<String> uniqueTextinColSelected22 =
		// FXCollections.observableArrayList();
		// for (int i = 0; i <= 100; i++) {
		// uniqueTextinColSelected22.add("Item " + i);
		// }
		//
		// // Create the CheckListView with the data
		// checkListView = new CheckListView<>(uniqueTextinColSelected22);
		checkListView.setItems(uniqueTextinColSelected);

		HBox buttongroup = new HBox(20);

		buttongroup.setAlignment(Pos.CENTER);
		buttongroup.getChildren().addAll(dataFilterReplaceDatasetButton, dataFilterSaveAsNewDatasetButton,
				dataFilterBackMain);

		container.getChildren().addAll(dFS_Warninglb, numberfiltertitlegroup, numberfiltergroup, new Separator(),
				textfiltertitlegroup, cbfortextfield, checkListView, new Separator(), buttongroup);
		container.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(container);
		pane.getStyleClass().add("screen-background");

		return pane;

	}

	/**
	 * Creates the main screen and layout its UI components
	 * 
	 * @return a Pane component to be displayed on a scene
	 */
	private Pane paneMainScreen() {

		lbMainScreenTitle = new Label("COMP3111 Chart");
		btSampleLineChartData = new Button("Sample 1");
		btSampleLineChartDataV2 = new Button("Sample 2");
		btSampleLineChart = new Button("Demo Plot Chart with random data");
		lbSampleDataTable = new Label("DataTable: empty");

		importButton = new Button("Import .csv");
		exportButton = new Button("Export .csv");
		savingButton = new Button("Save as .comp3111");
		loadingButton = new Button("Load .comp3111 to our program");
		dataFilteringAndTransformationButton = new Button("Data Filtering and Transformation");
		plotGraphButton = new Button("Plot Graph with selected dataset");

		showChartButton = new Button("Show Chart");
		showDataLabel = new Label("Welcome!");
		datasetlabel = new Label("Dataset");
		chartlabel = new Label("Chart");

		dataSetPreviewButton = new Button("Data set preview");

		datasetslist.setItems(datasetsname);

		chartslist.setItems(chartsname);

		datasetslist.setPrefWidth(400);
		datasetslist.setPrefHeight(300);

		chartslist.setPrefWidth(400);
		chartslist.setPrefHeight(300);
		// Layout the UI components

		HBox groupofList = new HBox(20);

		groupofList.getChildren().addAll(datasetslist, chartslist);

		groupofList.setAlignment(Pos.CENTER);
		HBox groupofBill = new HBox(20);

		groupofBill.setAlignment(Pos.CENTER);
		groupofBill.getChildren().addAll(importButton, exportButton, savingButton, loadingButton);

		HBox groupofChartandTrans = new HBox(20);
		groupofChartandTrans.setAlignment(Pos.CENTER);
		groupofChartandTrans.getChildren().addAll(dataSetPreviewButton, dataFilteringAndTransformationButton,
				showChartButton, plotGraphButton);

		// this below can be delete
		HBox hc = new HBox(20);
		hc.setAlignment(Pos.CENTER);
		hc.getChildren().addAll(btSampleLineChartData, btSampleLineChartDataV2);

		// above can be delete
		HBox namegroup = new HBox(200);
		namegroup.setAlignment(Pos.CENTER);
		namegroup.getChildren().addAll(datasetlabel, showDataLabel, chartlabel);

		VBox container = new VBox(20);
		container.getChildren().addAll(groupofBill, lbMainScreenTitle, new Separator(), namegroup, groupofList,
				new Separator(), groupofChartandTrans);
		container.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(container);

		// Apply style to the GUI components
		btSampleLineChart.getStyleClass().add("menu-button");
		lbMainScreenTitle.getStyleClass().add("menu-title");
		pane.getStyleClass().add("screen-background");

		return pane;
	}

	/**
	 * Creates the graph type screen and layout its UI components
	 * 
	 * @return a Pane component to be displayed on a graph type selection scene
	 */

	private Pane panePlotGraphScreen() {
		chartSelectionDataLabel = new Label("Please select a chart type ");
		chartInvalidDataset = new Label("");
		plotChartBackMainBtn = new Button("Back");
		selectChartTypeBtn = new Button("Select Chart Type");
		plotChartTypeList.setItems(chartTypesName);

		plotChartTypeList.setPrefWidth(400);
		plotChartTypeList.setPrefHeight(300);

		HBox title = new HBox(20);
		title.getChildren().add(chartSelectionDataLabel);
		title.setAlignment(Pos.CENTER);

		HBox errorMsg = new HBox(20);
		errorMsg.getChildren().add(chartInvalidDataset);
		errorMsg.setAlignment(Pos.CENTER);

		HBox groupofList = new HBox(20);
		groupofList.getChildren().add(plotChartTypeList);
		groupofList.setAlignment(Pos.CENTER);

		HBox groupofButtons = new HBox(20);
		groupofButtons.getChildren().addAll(selectChartTypeBtn, plotChartBackMainBtn);
		groupofButtons.setAlignment(Pos.CENTER);

		VBox container = new VBox(20);
		container.getChildren().addAll(title, errorMsg, groupofList, groupofButtons);
		container.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(container);
		pane.getStyleClass().add("screen-background");

		return pane;

	}

	/**
	 * Creates the Axis selection screen and layout its UI components
	 * 
	 * @return a Pane component to be displayed on a Axis selection scene
	 */

	private Pane paneAxisScreen() {
		axisSelectionLabel = new Label("");
		invalidAxis = new Label("");
		xAxisLabel = new Label("X Axis");
		yAxisLabel = new Label("Y Axis");
		btAxisBack = new Button("Back");
		selectAxisBtn = new Button("Select columns");

		dataColumnslistX.setPrefWidth(350);
		dataColumnslistX.setPrefHeight(300);
		dataColumnslistY.setPrefWidth(350);
		dataColumnslistY.setPrefHeight(300);

		HBox title = new HBox(20);
		title.getChildren().add(axisSelectionLabel);
		title.setAlignment(Pos.CENTER);

		HBox errorMsg = new HBox(20);
		errorMsg.getChildren().add(invalidAxis);
		errorMsg.setAlignment(Pos.CENTER);

		VBox xAxisBox = new VBox(20);
		VBox yAxisBox = new VBox(20);

		switch (graphSelectedIndex) {
		case 0:
		case 2:
			axisSelectionLabel.setText("Please select columns for X and Y axis");
			comboBoxX = new ComboBox();
			comboBoxY = new ComboBox();

			xAxisBox.getChildren().addAll(xAxisLabel, comboBoxX);
			xAxisBox.setAlignment(Pos.CENTER);

			yAxisBox.getChildren().addAll(yAxisLabel, comboBoxY);
			yAxisBox.setAlignment(Pos.CENTER);
			break;

		case 1:
			axisSelectionLabel
					.setText("Please select columns for X and Y axis (Press Ctrl or Shift for multiple selection)");
			xAxisBox.getChildren().addAll(xAxisLabel, dataColumnslistX);
			xAxisBox.setAlignment(Pos.CENTER);

			yAxisBox.getChildren().addAll(yAxisLabel, dataColumnslistY);
			yAxisBox.setAlignment(Pos.CENTER);
			break;
		}

		HBox axisListBox = new HBox(20);
		axisListBox.getChildren().addAll(xAxisBox, yAxisBox);
		axisListBox.setAlignment(Pos.CENTER);

		HBox groupofButtons = new HBox(20);
		groupofButtons.getChildren().addAll(selectAxisBtn, btAxisBack);
		groupofButtons.setAlignment(Pos.CENTER);

		VBox container = new VBox(20);
		container.getChildren().addAll(title, errorMsg, axisListBox, groupofButtons);
		container.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(container);
		pane.getStyleClass().add("screen-background");
		return pane;
	}

	/**
	 * Creates the Chart screen and layout its UI components. Creates chart
	 * accordingly
	 * 
	 * @return a Pane component to be displayed on a Chart scene
	 */
	private Pane paneChartScreen(Chart chart) {
		btChartBack = new Button("Back");
		btChartBackMain = new Button("Back to main");
		saveChartBtn = new Button("Save chart");

		HBox chartContainer = new HBox(20);
		switch (chart.getTypeID()) {
		case 0:
			chartContainer.getChildren().clear();
			xAxisNum = new NumberAxis();
			yAxisNum = new NumberAxis();
			lineChartFinal = new LineChart<Number, Number>(xAxisNum, yAxisNum);
			lineChartFinal.setTitle("An empty line chart");
			xAxisNum.setLabel("");
			yAxisNum.setLabel("");

			chartContainer.getChildren().add(lineChartFinal);
			break;
		case 1:
			chartContainer.getChildren().clear();
			xAxisTxt = new CategoryAxis();
			yAxisNum = new NumberAxis();
			barChartFinal = new BarChart<String, Number>(xAxisTxt, yAxisNum);
			barChartFinal.setTitle("An empty bar chart");
			xAxisTxt.setLabel("");
			yAxisNum.setLabel("");

			chartContainer.getChildren().add(barChartFinal);

			break;

		case 2:
			chartContainer.getChildren().clear();
			xAxisNum = new NumberAxis();
			yAxisNum = new NumberAxis();
			animatedLineChartFinal = new LineChart<Number, Number>(xAxisNum, yAxisNum);
			animatedLineChartFinal.setTitle("An empty animated line chart");
			xAxisNum.setLabel("");
			yAxisNum.setLabel("");

			chartContainer.getChildren().add(animatedLineChartFinal);
			break;
		}

		chartContainer.setAlignment(Pos.CENTER);

		HBox container = new HBox(20);
		container.getChildren().addAll(saveChartBtn, btChartBackMain, btChartBack);
		container.setAlignment(Pos.CENTER);

		VBox totalContainer = new VBox(20);
		totalContainer.getChildren().addAll(chartContainer, container);
		totalContainer.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(totalContainer);
		// Apply CSS to style the GUI components
		pane.getStyleClass().add("screen-background");
		return pane;
	}

	/**
	 * This method is used to pick anyone of the scene on the stage. It handles the
	 * hide and show order. In this application, only one active scene should be
	 * displayed on stage.
	 * 
	 * @param sceneID
	 *            - The sceneID defined above (see SCENE_XXX)
	 */
	private void putSceneOnStage(int sceneID) {

		updateDatasetsListandChartList();

		// ensure the sceneID is valid
		if (sceneID < 0 || sceneID >= SCENE_NUM)
			return;

		stage.hide();
		stage.setTitle(SCENE_TITLES[sceneID]);
		stage.setScene(scenes[sceneID]);
		stage.setResizable(true);
		stage.show();
	}

	/**
	 * 
	 * All JavaFx GUI application needs to override the start method You can treat
	 * it as the main method (i.e. the entry point) of the GUI application
	 */
	@Override
	public void start(Stage primaryStage) {
		try {

			// update List View
			updateDatasetsListandChartList();

			stage = primaryStage; // keep a stage reference as an attribute
			initScenes(); // initialize the scenes
			initEventHandlers(); // link up the event handlers
			putSceneOnStage(SCENE_MAIN_SCREEN); // show the main screen

		} catch (Exception e) {

			e.printStackTrace(); // exception handling: print the error message on the console
		}
	}

	/**
	 * main method - only use if running via command line
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}
}
