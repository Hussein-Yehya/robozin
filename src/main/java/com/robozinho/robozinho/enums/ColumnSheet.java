package com.robozinho.robozinho.enums;

import java.util.EnumMap;

public enum ColumnSheet {

	ID(0), NOME(1), NOTA(2), AVALIACAO_POSITIVA(3), AVALIACAO_NEGATIVA(4), TELEFONE(5), ESTADO(6), CIDADE(7),
	BAIRRO(8), RUA(9), NUMERO(10), LATITUDE(11), LONGITUDE(12), CATEGORIA(13);

	public Integer indexColumns;

	public Integer indexColumns() {
		return indexColumns;
	}

	ColumnSheet(Integer indexColumns) {
		this.indexColumns = indexColumns;
	}

	public static EnumMap<ColumnSheet, Integer> columnMap() {

		EnumMap<ColumnSheet, Integer> enumMap = new EnumMap<ColumnSheet, Integer>(ColumnSheet.class);

		enumMap.put(ColumnSheet.ID, ColumnSheet.ID.indexColumns);
		enumMap.put(ColumnSheet.NOME, ColumnSheet.NOME.indexColumns);
		enumMap.put(ColumnSheet.NOTA, ColumnSheet.NOTA.indexColumns);
		enumMap.put(ColumnSheet.AVALIACAO_POSITIVA, ColumnSheet.AVALIACAO_POSITIVA.indexColumns);
		enumMap.put(ColumnSheet.AVALIACAO_NEGATIVA, ColumnSheet.AVALIACAO_NEGATIVA.indexColumns);
		enumMap.put(ColumnSheet.TELEFONE, ColumnSheet.TELEFONE.indexColumns);
		enumMap.put(ColumnSheet.ESTADO, ColumnSheet.ESTADO.indexColumns);
		enumMap.put(ColumnSheet.CIDADE, ColumnSheet.CIDADE.indexColumns);
		enumMap.put(ColumnSheet.BAIRRO, ColumnSheet.BAIRRO.indexColumns);
		enumMap.put(ColumnSheet.RUA, ColumnSheet.RUA.indexColumns);
		enumMap.put(ColumnSheet.NUMERO, ColumnSheet.NUMERO.indexColumns);
		enumMap.put(ColumnSheet.LATITUDE, ColumnSheet.LATITUDE.indexColumns);
		enumMap.put(ColumnSheet.LONGITUDE, ColumnSheet.LONGITUDE.indexColumns);
		enumMap.put(ColumnSheet.CATEGORIA, ColumnSheet.CATEGORIA.indexColumns);

		return enumMap;
	}

}
