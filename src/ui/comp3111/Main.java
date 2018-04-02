package ui.comp3111;

import core.comp3111.DataColumn;
import core.comp3111.DataManager;
import core.comp3111.DataTable;
import core.comp3111.DataType;
import core.comp3111.IOManager;
import core.comp3111.SampleDataGenerator;
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
	
	private int datasetsSelectedIndex = 0;
	private ObservableList<String> datasetsname = FXCollections.observableArrayList ();
	
	private ListView<String> chartslist = new ListView<String>();
	private ObservableList<String> chartsname =FXCollections.observableArrayList ("Chart 1", "Chart 2", "Chart 3", "Chart 4");
	
	//Just for testing puropose
	
	//ObservableList<String> items =FXCollections.observableArrayList ("Single", "Double", "Suite", "Family App");
	//list.setItems(items);
	// Attributes: Scene and Stage
	
	
	private static final int SCENE_NUM = 3;
	private static final int SCENE_MAIN_SCREEN = 0;
	private static final int SCENE_LINE_CHART = 1;;
	private static final int SCENE_DATA_FILTER = 2;
	private static final String[] SCENE_TITLES = { "COMP3111 Chart - Pepsi", "Sample Line Chart Screen","Data filtering and transformation" };
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
	

	/**
	 * create all scenes in this application
	 */
	private void initScenes() {
		scenes = new Scene[SCENE_NUM];
		scenes[SCENE_MAIN_SCREEN] = new Scene(paneMainScreen(), 1000, 1000);
		scenes[SCENE_LINE_CHART] = new Scene(paneLineChartScreen(), 800, 600);

		scenes[SCENE_DATA_FILTER] = new Scene(paneDataFilterScreen(), 800, 600);

		//scenes[SCENE_LINE_CHART] = new Scene(paneLineChartScreen(), 700, 600);
		for (Scene s : scenes) {
			if (s != null)
				// Assumption: all scenes share the same stylesheet
				s.getStylesheets().add("Main.css");
		}

	}
	
	
	private void updateDatasetsListandChartList() {
		
		
		//TODO 
		
		
		datasetsname.clear();
		
		for(int i=0;i<dataTables.size();i++) {

			datasetsname.add("Dataset "+String.valueOf(i+1)); 
		}

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
			} catch (FileNotFoundException e1) {
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
				DataManager.dataExport(sampleDataTable, stage);
				
				
				showDataLabel.setText(String.format("Welcome!"));
				
			}
		});
		
		savingButton.setOnAction(e -> {
			//Bill Please add your function here
			//save dataTables and TODO charts
			IOManager.fileExport(dataTables, null, stage);
			
		});
		
		loadingButton.setOnAction(e -> {
			//Bill Please add your function here
			
			dataTables.add(SampleDataGenerator.generateSampleLineData());
			IOManager.fileImport(stage);
			
			//Please put corresponding datatables and charts ArrayList into attribute in this class.
			IOManager.getDataTables();
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
				System.out.println("Please select a dataset" );
			}else {
				sampleDataTable = dataTables.get(datasetsSelectedIndex);
				
				//use the sampleDataTable variable to plot graph 

				//Won Please add your function here
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

	/**
	 * Creates the main screen and layout its UI components
	 * 
	 * @return a Pane component to be displayed on a scene
	 */
	
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
