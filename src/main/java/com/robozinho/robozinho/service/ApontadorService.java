package com.robozinho.robozinho.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.robozinho.robozinho.enums.ColumnSheet;
import com.robozinho.robozinho.model.Apontador;
import com.robozinho.robozinho.model.Place;

@Service
public class ApontadorService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String API = "https://API.apontador.com.br/v2/places/";

	private static final String MAX_ROWS = "50";

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";

	private static final String ACCESS_TOKEN = "Bearer 5b54a561-dc8d-4b91-9368-8203c238fc09";

	private static final int MAX_SIZE = 45000;

	private static volatile int start = -50;

	private static int column = -1;

	private static int rowNumber = 1;

	private String nameSheet = "Restaurantes";

	private ExecutorService service = Executors.newFixedThreadPool(160);

	private Map<String, Workbook> map = new HashMap<String, Workbook>();

	public void pegaInformacoes(String category) throws IOException, URISyntaxException {
		int total = 500000;
		int count = 0;

		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> entity = this.buildHeader();

		List<Apontador> apontadors = new ArrayList<>();

		while (count < total) {

			service.execute(new Runnable() {

				@Override
				public void run() {
					start += 50;
					try {
						logger.info("######### START {} ", start);
						this.extracted(category, start, restTemplate, entity, API, apontadors);
					} catch (InterruptedException | IOException | URISyntaxException e) {
						logger.error("VISH, DEU ERROR", e.getCause());
					}

				}

				private void extracted(String category, int start, RestTemplate restTemplate, HttpEntity<String> entity,
						String API, List<Apontador> apontadors)
						throws InterruptedException, ClientProtocolException, IOException, URISyntaxException {

					try {

						URIBuilder builder = new URIBuilder(API);
						builder.setParameter("q", category).setParameter("start", String.valueOf(start))
								.setParameter("rows", MAX_ROWS);

						HttpClient client = HttpClientBuilder.create().build();
						HttpGet request = new HttpGet(builder.build());
						request.addHeader("User-Agent", USER_AGENT);
						request.addHeader("Authorization", ACCESS_TOKEN);

						HttpResponse response = client.execute(request);

						BufferedReader rd = new BufferedReader(
								new InputStreamReader(response.getEntity().getContent()));

						StringBuffer result = new StringBuffer();
						String line = "";
						while ((line = rd.readLine()) != null) {
							result.append(line);
						}

						if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {

							Apontador apontadorResponse = new Gson().fromJson(result.toString(), Apontador.class);
							if (!apontadorResponse.getResults().getPlaces().isEmpty()) {

								apontadors.add(apontadorResponse);
							} else {
								try {
									logger.info("finalizando as thread");
									this.finalize();
								} catch (Throwable e) {
									logger.error("Erro ao finalizar as thread ", e);
								}
							}

						} else if (response.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
							Thread.sleep(10000);
						}
					} catch (RestClientException e) {
						logger.error("####### DEEEEU RUIMMM", e);
					}

				}

			});

			count += 50;

		}
		service.shutdown();
		try {
			service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			logger.error("Erro {}", e);
		}

		logger.info("Iniciando o processo de escrita");
		this.buildCSV(apontadors, category);

	}

	private void buildCSV(List<Apontador> apontadores, String category) throws IOException {

		List<Place> places = apontadores.stream().flatMap(apontador -> apontador.getResults().getPlaces().stream())
				.collect(Collectors.toList());

		Workbook workbook = new XSSFWorkbook();

		Sheet sheet = workbook.createSheet(nameSheet.concat(" - 1"));
		sheet.setColumnWidth(0, 6000);

		Row header = sheet.createRow(0);

		CellStyle headerStyle = workbook.createCellStyle();

		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 11);
		font.setBold(true);
		headerStyle.setFont(font);

		this.createHeaderSheet(header, headerStyle);

		int part = 1;

		if (!places.isEmpty()) {
			logger.info("Tamanho da lista ########################### {}", places.size());
			for (Place place : places) {

				Row row = sheet.createRow(rowNumber);

				if (rowNumber == MAX_SIZE || rowNumber == places.size()) {
					logger.info("########################### Criando Arquivo xlsx ###########################");

					File currDir = new File("/Users/ext_hali/workspace-spring/robozinho/target/classes/" + category
							+ "-part-" + part + "-.xlsx");
					String path = currDir.getAbsolutePath();

					map.put(path, workbook);

					map.forEach((k, v) -> {
						FileOutputStream outputStream = null;
						try {
							outputStream = new FileOutputStream(k);
							v.write(outputStream);
							v.close();
						} catch (IOException e) {
							logger.error("Erro ao tentar salvar o arquivo | Causa: {}", e);
						}

					});

					map.clear();
					workbook = new XSSFWorkbook();

					sheet = workbook.createSheet(nameSheet);
					sheet.setColumnWidth(0, 6000);

					header = sheet.createRow(0);

					this.createHeaderSheet(header, headerStyle);

					rowNumber = 0;
					part++;
				}

				place.placeMap().forEach((k, v) -> {
					column++;
					Cell cell = row.createCell(column);
					cell.setCellValue(v);
				});

				column = -1;
				rowNumber++;

			}
		}

		workbook.close();
	}

	private void createHeaderSheet(Row header, CellStyle headerStyle) {
		ColumnSheet.columnMap().forEach((k, v) -> {

			Cell headerCell = header.createCell(v.intValue());
			headerCell.setCellValue(k.name());

		});
	}

	private HttpEntity<String> buildHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent", USER_AGENT);
		headers.setBearerAuth(ACCESS_TOKEN);

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return entity;
	}

}
