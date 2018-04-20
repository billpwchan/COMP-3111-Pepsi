package ui.comp3111;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;
import core.comp3111.SampleDataGenerator;
import core.comp3111.Chart;
import core.comp3111.LineChartP;
import core.comp3111.BarChartP;
import core.comp3111.AnimatedLineChart;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Arrays;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.StackPane;

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
	private ObservableList<String> chartTypesName = FXCollections.observableArrayList ("Line Chart", "Bar Chart", "Animated Line Chart");
	
	private int datasetsSelectedIndex = 0;
	private ObservableList<String> datasetsname = FXCollections.observableArrayList ();
	
	private List<Chart> charts = new ArrayList<Chart>();
	private ListView<String> chartslist = new ListView<String>();
	private ObservableList<String> chartsname =FXCollections.observableArrayList ();

	
	private List<DataColumn> dataColumns = new ArrayList<DataColumn>();
	private ListView<String> dataColumnslistX = new ListView<String>();
	private ListView<String> dataColumnslistY = new ListView<String>();
	private ObservableList<String> dataColumnsName =FXCollections.observableArrayList ();
	
	//Just for testing puropose
	
	//ObservableList<String> items =FXCollections.observableArrayList ("Single", "Double", "Suite", "Family App");
	//list.setItems(items);
	// Attributes: Scene and Stage
	
	
	private static final int SCENE_NUM = 6;
	private static final int SCENE_MAIN_SCREEN = 0;
	private static final int SCENE_LINE_CHART = 1;;
	private static final int SCENE_DATA_FILTER = 2;
	private static final int PLOT_GRAPH = 3;
	private static final int SCENE_AXIS_SELECT = 4;
	private static final int SCENE_CHART = 5;
	private static final String[] SCENE_TITLES = { "COMP3111 Chart - Pepsi", "Sample Line Chart Screen","Data filtering and transformation", "Plot graph with selected dataset", "Select axis", "Chart view" };
	private Stage stage = null;
	private Scene[] scenes = null;

	// To keep this application more structural,
	// The following UI components are used to keep references after invoking
	// createScene()
	// Screen 1: paneMainScreen
	private Button dataSetPreviewButton, dataFilterReplaceDatasetButton, dataFilterSaveAsNewDatasetButton, dataFilterBackMain, btSampleLineChartData, btSampleLineChartDataV2, btSampleLineChart, importButton, exportButton, savingButton, loadingButton, dataFilteringAndTransformationButton, plotGraphButton,showChartButton ;
	private Label lbSampleDataTable, lbMainScreenTitle, showDataLabel;	

	//private ListView<DataTable> datasetListView;
	
	       
	
	//private ListView<> ChartListView;

	// Screen 2: paneSampleLineChartScreen
	private LineChart<Number, Number> lineChart = null;
	private NumberAxis xAxis = null;
	private NumberAxis yAxis = null;
	private Button btLineChartBackMain = null;
	
	// Screen 3 :panePlotGraphScreen
	private Button plotChartBackMainBtn, selectChartTypeBtn;
	private Label chartSelectionDataLabel,chartInvalidDataset;

	// Screen 4: paneAxisScreen
	private Button btAxisBack, selectAxisBtn;
	private Label axisSelectionLabel,invalidAxis, xAxisLabel, yAxisLabel;
	private ObservableList<String> selectedColumnsX = null;
	private ObservableList<String> selectedColumnsY = null;
	
	// Screen 5 : paneChartScreen
	private Chart chart = null;
	private Button btChartBackMain, saveChartBtn, btChartBack ;
	

	
	/**
	 * create all scenes in this application
	 */
	private void initScenes() {
		scenes = new Scene[SCENE_NUM];
		scenes[SCENE_MAIN_SCREEN] = new Scene(paneMainScreen(), 850, 850);
		scenes[SCENE_LINE_CHART] = new Scene(paneLineChartScreen(), 800, 600);

		scenes[SCENE_DATA_FILTER] = new Scene(paneDataFilterScreen(), 800, 600);
		scenes[PLOT_GRAPH] = new Scene(panePlotGraphScreen(), 800, 600);
		scenes[SCENE_AXIS_SELECT] = new Scene(paneAxisScreen(), 800, 600);
		scenes[SCENE_CHART] = new Scene(paneChartScreen(), 800, 600);
		
		
		//scenes[SCENE_LINE_CHART] = new Scene(paneLineChartScreen(), 700, 600);
		for (Scene s : scenes) {
			if (s != null)
				// Assumption: all scenes share the same stylesheet
				s.getStylesheets().add("Main.css");
		}

	}
	
	
	/**
	 * update the dataset list and  chart list on the main screen
	 */
	private void updateDatasetsListandChartList() {
		
		
		//TODO 
		
		datasetsname.clear();
		
		for(int i=0;i<dataTables.size();i++) {

			datasetsname.add("Dataset "+String.valueOf(i+1)); 
		}
		

		
		chartsname.clear();
		for(int i = 0; i<charts.size();++i) {
			chartsname.add("Chart" + String.valueOf(i+1));
		}
		
		//highlight and select the newly added dataset by default
		datasetslist.getSelectionModel().select(dataTables.size()-1);
		chartslist.getSelectionModel().select(charts.size()-1);
		
	}

	
	/**
	 * update the column liston the axis selection screen
	 */
	private void updateAxisList() {
		dataColumnsName.clear();
		
		DataTable tempDataTable = dataTables.get(datasetsSelectedIndex);
		dataColumns = tempDataTable.getAllColValue();
		List<String> tempColName = tempDataTable.getAllColName();
		
		
		int numCol = tempDataTable.getNumCol();
		
		
		for(int i = 0; i<numCol; ++i) {
			dataColumnsName.add(tempColName.get(i));
		}
		
		
		dataColumnslistX.setItems(dataColumnsName);
		dataColumnslistY.setItems(dataColumnsName);
		
		dataColumnslistX.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		dataColumnslistY.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		
		//select the first column on the axis selection list by default 
		dataColumnslistX.getSelectionModel().select(0);
		dataColumnslistY.getSelectionModel().select(0);
		
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
		initAxisScreenHandlers();
		initChartScreenHandlers();
		
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
		
		dataFilterReplaceDatasetButton.setOnAction(e -> {
			//TODO save the modify dataset to same dataset sampleDataTable
			
			
			
			
			//dataTables.listIterator(datasetsSelectedIndex);
			dataTables.remove(datasetsSelectedIndex);
			dataTables.add(datasetsSelectedIndex, sampleDataTable);
			updateDatasetsListandChartList();
			
			putSceneOnStage(SCENE_MAIN_SCREEN);

		});
		
		dataFilterSaveAsNewDatasetButton.setOnAction(e -> {
			
			//TODO modify the sampleDataTable
			
			dataTables.add(sampleDataTable);
			
			updateDatasetsListandChartList();
			
			putSceneOnStage(SCENE_MAIN_SCREEN);

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
			System.out.println(graphSelectedIndex);
			//data requirement check
			switch(graphSelectedIndex)
			{
			case 0: 
				//Line Chart
				LineChartP lineChart = new LineChartP(dataTables.get(datasetsSelectedIndex));
				if(lineChart.dataRequirementValidation())		
				{
					putSceneOnStage(SCENE_AXIS_SELECT);
					chartInvalidDataset.setText("");
				}
				else
					chartInvalidDataset.setText("Line chart requires at least 2 numeric columns!");
				break;
				
			case 1:
				//Bar Chart
				BarChartP barChart = new BarChartP(dataTables.get(datasetsSelectedIndex));
				if(barChart.dataRequirementValidation())
				{
					putSceneOnStage(SCENE_AXIS_SELECT);
					chartInvalidDataset.setText("");
				}
				else
					chartInvalidDataset.setText("Bar chart requires at least 1 text column and 1 numeric column!");								
				break;
				
			case 2:
				//Animated Line Chart
				AnimatedLineChart animatedLineChart = new AnimatedLineChart(dataTables.get(datasetsSelectedIndex));
				if(animatedLineChart.dataRequirementValidation())
				{
					putSceneOnStage(SCENE_AXIS_SELECT);
					chartInvalidDataset.setText("");
				}
				else
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
			selectedColumnsX =  dataColumnslistX.getSelectionModel().getSelectedItems();
			selectedColumnsY =  dataColumnslistY.getSelectionModel().getSelectedItems();
			DataTable selectedDT = dataTables.get(datasetsSelectedIndex);
			DataColumn tempCol = null;
			int numColnumX = 0;
			int textColnumX = 0;
			int numColnumY = 0;
			int textColnumY = 0;
//count number of selected numeric and text columns for X and Y axis			
			for(int i =0; i<selectedColumnsX.size(); ++i)
			{
				tempCol = selectedDT.getCol(selectedColumnsX.get(i));
				if(tempCol.getTypeName().equals(DataType.TYPE_NUMBER))
					numColnumX++;
				if(tempCol.getTypeName().equals(DataType.TYPE_STRING))
					textColnumX++;
			}
			System.out.println(numColnumX+" "+ textColnumX + " " + numColnumY + " " + textColnumY);
			
			for(int i =0; i<selectedColumnsY.size(); ++i)
			{
				tempCol = selectedDT.getCol(selectedColumnsY.get(i));
				if(tempCol.getTypeName().equals(DataType.TYPE_NUMBER))
					numColnumY++;
				if(tempCol.getTypeName().equals(DataType.TYPE_STRING))
					textColnumY++;
			}
			
			System.out.println(numColnumX+" "+ textColnumX + " " + numColnumY + " " + textColnumY);
		 			
			//Add chart
			switch(graphSelectedIndex)
			{
			case 0: 
				//Line Chart
				LineChartP lineChart = new LineChartP(dataTables.get(datasetsSelectedIndex));
				
				charts.add(lineChart);
				
				
				
				
				break;
				
			case 1:
				//Bar Chart
				BarChartP barChart = new BarChartP(dataTables.get(datasetsSelectedIndex));
				charts.add(barChart);				
				break;
				
			case 2:
				//Animated Line Chart
				AnimatedLineChart animatedLineChart = new AnimatedLineChart(dataTables.get(datasetsSelectedIndex));
				charts.add(animatedLineChart);	
				break;
				
			}
			
			
			putSceneOnStage(SCENE_CHART);
			invalidAxis.setText("");
		});
		
	}
	
	/**
	 * Initialize event handlers of the Chart screen
	 */
	private void initChartScreenHandlers() {

		// click handler
		btChartBack.setOnAction(e -> {
			charts.remove(charts.size()-1);
			putSceneOnStage(SCENE_AXIS_SELECT);
		});
		
		btChartBackMain.setOnAction(e -> {
			charts.remove(charts.size()-1);
			putSceneOnStage(SCENE_MAIN_SCREEN);
		});
		
		
		saveChartBtn.setOnAction(e -> {
			
			putSceneOnStage(SCENE_MAIN_SCREEN);
		});
		
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
			sampleDataTable = SampleDataGenerator.generateSampleLineData();
			lbSampleDataTable.setText(String.format("SampleDataTable: %d rows, %d columns", sampleDataTable.getNumRow(),
					sampleDataTable.getNumCol()));

			populateSampleDataTableValuesToChart("Sample 10");

		});

		// click handler
		btSampleLineChartDataV2.setOnAction(e -> {

			// In this example, we invoke SampleDataGenerator to generate sample data
			sampleDataTable = SampleDataGenerator.generateSampleLineDataV2();
			lbSampleDataTable.setText(String.format("SampleDataTable: %d rows, %d columns", sampleDataTable.getNumRow(),
					sampleDataTable.getNumCol()));

			populateSampleDataTableValuesToChart("Sample 2");
			
		});

		// click handler
		btSampleLineChart.setOnAction(e -> {
			
			putSceneOnStage(SCENE_LINE_CHART);
			
			
		});
		
		showChartButton.setOnAction(e -> {
			
			//Won your show chart function here
			//Log G will pass you the chart
			//delete the line below if u need
			putSceneOnStage(SCENE_LINE_CHART);
			
			
		});
		
		
		importButton.setOnAction(e -> {
			//Will provide a file chooser, return with a dataTable object.
			try {
				//temp will return null if user cancel action in the middle.
				DataTable temp = DataManager.dataImport(stage);
				if (temp != null) { dataTables.add(temp); } 
			} catch (FileNotFoundException | DataTableException e1) {
				e1.printStackTrace();
			} 			
			
			updateDatasetsListandChartList();

			
		});
		
		exportButton.setOnAction(e -> {
			//Log G: If a chart is selected, should not allow the user to use it! 
			
			datasetsSelectedIndex = datasetslist.getFocusModel().getFocusedIndex();
			System.out.println(datasetsSelectedIndex );
			if (datasetsSelectedIndex==-1) {
				showDataLabel.setText(String.format("Please select a dataset to export to .csv"));
				
			}else {
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
			//Bill Please add your function here
			//save dataTables and TODO charts
			try {
				IOManager.fileExport(dataTables, null, stage);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		
		loadingButton.setOnAction(e -> {
			//Bill Please add your function here
			
			try {
				IOManager.fileImport(stage);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//Please put corresponding datatables and charts ArrayList into attribute in this class.
			List<DataTable> inputDataTable = IOManager.getDataTables();
			for (DataTable DataTableObj : inputDataTable) {
				dataTables.add(DataTableObj);
			}
			IOManager.getCharts();
			

			updateDatasetsListandChartList();
			
		});
		
		dataFilteringAndTransformationButton.setOnAction(e -> {
			//Log G Please add your function here
			
			datasetsSelectedIndex = datasetslist.getFocusModel().getFocusedIndex();
			System.out.println(datasetsSelectedIndex );
			if (datasetsSelectedIndex==-1) {
				showDataLabel.setText(String.format("Please select a dataset to do filtering and transformation."));
				
			}else {
				sampleDataTable = dataTables.get(datasetsSelectedIndex);
				putSceneOnStage(SCENE_DATA_FILTER);	
				showDataLabel.setText(String.format("Welcome!"));
				
			}
			
			

			updateDatasetsListandChartList();
		});
		
		dataSetPreviewButton.setOnAction(e -> {
			
			datasetsSelectedIndex = datasetslist.getFocusModel().getFocusedIndex();
			
			sampleDataTable = dataTables.get(datasetsSelectedIndex);
			
			if (datasetsSelectedIndex==-1||sampleDataTable==null) {
				showDataLabel.setText(String.format("No dataset being selected to be preview"));
				
			}else {
				sampleDataTable = dataTables.get(datasetsSelectedIndex);
				showDataLabel.setText(String.format("Dataset %d : %d rows, %d columns",datasetsSelectedIndex+1, sampleDataTable.getNumRow(),
						sampleDataTable.getNumCol()));

		
			}
			
			

			updateDatasetsListandChartList();
		});
		
		
		
		plotGraphButton.setOnAction(e -> {
			
			datasetsSelectedIndex = datasetslist.getFocusModel().getFocusedIndex();
			System.out.println(datasetsSelectedIndex );
			if (datasetsSelectedIndex==-1) {
				showDataLabel.setText(String.format("Please select a dataset to plot a graph."));
			}else {
				sampleDataTable = dataTables.get(datasetsSelectedIndex);
				
				//use the sampleDataTable variable to plot graph 

				//Won Please add your function here
				//select Line Chart option by default and put the chart selection scene on
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
		
		VBox container = new VBox(20);
		
		dataFilterReplaceDatasetButton = new Button("Replacing the current dataset");
		
		
		
		dataFilterSaveAsNewDatasetButton = new Button("Save as new dataset");
		dataFilterBackMain = new Button("Back");
		

		HBox buttongroup = new HBox(20);
		

		buttongroup.setAlignment(Pos.CENTER);
		buttongroup.getChildren().addAll(dataFilterReplaceDatasetButton, dataFilterSaveAsNewDatasetButton, dataFilterBackMain);
		
		container.getChildren().addAll(buttongroup);
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

		dataSetPreviewButton = new Button("Data set preview");
		
		datasetslist.setItems(datasetsname);

		chartslist.setItems(chartsname);
		
		datasetslist.setPrefWidth(400);
		datasetslist.setPrefHeight(300);
		
		chartslist.setPrefWidth(400);
		chartslist.setPrefHeight(300);
		// Layout the UI components

		HBox groupofList = new HBox(20);
		
		groupofList.getChildren().addAll(datasetslist, chartslist );

		groupofList.setAlignment(Pos.CENTER);
		HBox groupofBill = new HBox(20);

		groupofBill.setAlignment(Pos.CENTER);
		groupofBill.getChildren().addAll(importButton, exportButton , savingButton ,loadingButton);

		
		HBox groupofChartandTrans = new HBox(20);
		groupofChartandTrans.setAlignment(Pos.CENTER);
		groupofChartandTrans.getChildren().addAll(dataSetPreviewButton, dataFilteringAndTransformationButton, showChartButton, plotGraphButton);

		HBox hc = new HBox(20);
		hc.setAlignment(Pos.CENTER);
		hc.getChildren().addAll(btSampleLineChartData, btSampleLineChartDataV2);

		VBox container = new VBox(20);
		container.getChildren().addAll(groupofBill,lbMainScreenTitle, hc, lbSampleDataTable, btSampleLineChart ,new Separator(), showDataLabel, groupofList, new Separator(), groupofChartandTrans);
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
		groupofButtons.getChildren().addAll(selectChartTypeBtn,plotChartBackMainBtn);
		groupofButtons.setAlignment(Pos.CENTER);
		
		VBox container = new VBox(20);
		container.getChildren().addAll(title,errorMsg, groupofList, groupofButtons);
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
	axisSelectionLabel = new Label("Please select columns for X and Y axis (Press Ctrl or Shift for multiple selection)");
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
	xAxisBox.getChildren().addAll(xAxisLabel, dataColumnslistX);
	xAxisBox.setAlignment(Pos.CENTER);
	
	VBox yAxisBox = new VBox(20);
	yAxisBox.getChildren().addAll(yAxisLabel, dataColumnslistY);
	yAxisBox.setAlignment(Pos.CENTER);
	
	HBox axisListBox = new HBox(20);
	axisListBox.getChildren().addAll(xAxisBox, yAxisBox);
	axisListBox.setAlignment(Pos.CENTER);
	
	HBox groupofButtons = new HBox(20);
	groupofButtons.getChildren().addAll(selectAxisBtn,btAxisBack);
	groupofButtons.setAlignment(Pos.CENTER);
	
	VBox container = new VBox(20);
	container.getChildren().addAll(title,errorMsg, axisListBox, groupofButtons);
	container.setAlignment(Pos.CENTER);
	
	
	BorderPane pane = new BorderPane();
	pane.setCenter(container);
	pane.getStyleClass().add("screen-background");
	return pane;
}
	
	
	
/**
 * Creates the Chart screen and layout its UI components
 * 
 * @return a Pane component to be displayed on a Chart scene
 */	
private Pane paneChartScreen() {
		btChartBack = new Button("Back");
		btChartBackMain = new Button("Back to main");
		saveChartBtn = new Button("Save chart");
		HBox container = new HBox(20);
		container.getChildren().addAll(saveChartBtn, btChartBackMain,btChartBack);
		container.setAlignment(Pos.CENTER);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(container);
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
			
			
			
			
			//update List View
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
