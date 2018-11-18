package ru.r8digitaltech7.shoppingmap;

import java.util.HashMap;
import java.util.HashMap;
import java.util.LinkedHashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

public class ShopmapLocalDB extends SQLiteOpenHelper {

	boolean isCreating = false;
	SQLiteDatabase currentDB = null;

	MainActivity mainActivity;

	private final  static String logTag = "SHOPMAP:ShopmapLocalDB";
	private static final String DATABASE_NAME = "shopmap_6.db";
	private static final int DATABASE_VERSION = 60;
	private static final String DATABASE_TABLE_NOMENCLATURE = "items_nomenclature";
	public static  final String[] DATABASE_TABLE_NOMENCLATURE_FIELDS = new String[] {
			"_id", "parent_id", // flickr id of the photo
			"name", "nomenclature_code" };
	private static final String DATABASE_TABLE_LISTS = "lists";
	public static final String[] DATABASE_TABLE_LISTS_FIELDS = new String[] {
			"_id", "name", "select_flag" };
	private static final String DATABASE_TABLE_LISTS_ITEMS = "lists_items";
	public static final String[] DATABASE_TABLE_LISTS_ITEMS_FIELDS = new String[] {
			"list_id", "item_id", "count", "taken_flag" };
	private static final String DATABASE_TABLE_USER_SETTINGS = "user_settings";
	private static final String DATABASE_TABLE_SHOPS = "shops";
	private static final String DATABASE_TABLE_SHOPS_ITEMS = "shops_items";
	//nomenclature data cash
	HashMap<Integer, Item> nomenclature;
	LinkedHashMap<Integer, shpmList> lists;

	public Item getItemFromNomenclatureArray(int p_item_id) {
		if (nomenclature == null) {
			nomenclature = getNomenclatureFromDB();
		}
		return nomenclature.get(p_item_id);
	}

	public LinkedHashMap<Integer, shpmList> getAllListsFromArray() {
		if (lists == null) {
			lists = getSavedLists();
		}
		return lists;
	}

	public HashMap<Integer, Item> getNomenclature() {
		if (nomenclature == null) {
			nomenclature = getNomenclatureFromDB();
		}
		return nomenclature;
	}

	public HashMap<Integer, Item> getNomenclatureRootItems() {
		Log.v(logTag, "getNomenclatureRootItems()");
		if (nomenclature == null) {
			nomenclature = getNomenclatureFromDB();
		}
		HashMap<Integer, Item> result = new HashMap<Integer, Item>();

		for (Item i : nomenclature.values()) {
			Log.v(logTag, i.getTitle() + "/parent_id=" + i.parent_id);
			if (i.parent_id == -1) {
				Log.v(logTag, "childs" + i.getChilds().toString());
				result.put(i.getId(), i);
			}
		}
		return result;
	}

	private static final String DATABASE_CREATE = "create table "
			+ DATABASE_TABLE_NOMENCLATURE + "(_id INTEGER PRIMARY KEY"
			+ " AUTOINCREMENT," + "parent_id INTEGER," + "name text,"
			+ "nomenclature_code text);" + " create table "
			+ DATABASE_TABLE_LISTS + "(_id INTEGER PRIMARY KEY"
			+ " AUTOINCREMENT," + "name text, select_flag INTEGER);"
			+ " create table " + DATABASE_TABLE_LISTS_ITEMS
			+ "(list_id INTEGER REFERENCES " + DATABASE_TABLE_LISTS + "(_id),"
			+ "item_id INTEGER REFERENCES " + DATABASE_TABLE_LISTS_ITEMS
			+ "(_id));";

	public ShopmapLocalDB(Context ctx) {

		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		Log.v(logTag, "new ShopmapLocalDB()");
		mainActivity = (MainActivity) ctx;

	}

	// +++++++++++++++++++++++++++++++++ 1. СЛУЖЕБНЫЕ ФУНКЦИИ
	// БД+++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		isCreating = true;
		currentDB = db;
		Log.v(logTag, "onCreate");
		Log.v(logTag, DATABASE_CREATE);

		String createNomen = "create table " + DATABASE_TABLE_NOMENCLATURE
				+ " (_id INTEGER PRIMARY KEY" + " AUTOINCREMENT,"
				+ "parent_id INTEGER," + "name text,"
				+ "nomenclature_code text," + "type INTEGER);";
		String createLists = "create table " + DATABASE_TABLE_LISTS
				+ "(_id INTEGER PRIMARY KEY" + " AUTOINCREMENT,"
				+ "name text, select_flag INTEGER);";
		String createListsItems = "create table " + DATABASE_TABLE_LISTS_ITEMS
				+ "(list_id INTEGER REFERENCES " + DATABASE_TABLE_LISTS
				+ "(_id)," + "item_id INTEGER REFERENCES "
				+ DATABASE_TABLE_NOMENCLATURE + "(_id), taken_flag INTEGER);";
		String createUserSettings = "create table " + DATABASE_TABLE_USER_SETTINGS
				+ "(main_sort INTEGER, list_sort INTEGER);";
		String createShops = "create table " + DATABASE_TABLE_SHOPS
				+ "(_id INTEGER PRIMARY KEY" + " AUTOINCREMENT, "
				+ "name text, adress text, map_picture text, info text);";
		String createShopsItems = "create table " + DATABASE_TABLE_SHOPS_ITEMS
				+ "(shop_id INTEGER REFERENCES " + DATABASE_TABLE_SHOPS
				+ "(_id)," + "item_id INTEGER REFERENCES "
				+ DATABASE_TABLE_NOMENCLATURE + "(_id), locatioX FLOAT, locationY FLOAT)";

		db.execSQL(createNomen);
		db.execSQL(createLists);
		db.execSQL(createListsItems);
		db.execSQL(createUserSettings);
		db.execSQL(createShops);
		db.execSQL(createShopsItems);

		generateAllDefaultData();
		isCreating = false;
		currentDB = null;

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.v(logTag, "onUpgrade");
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NOMENCLATURE);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_LISTS);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_LISTS_ITEMS);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_USER_SETTINGS);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_SHOPS);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_SHOPS_ITEMS);

		onCreate(db);

	}

	@Override
	public SQLiteDatabase getWritableDatabase() {
		Log.v(logTag, "getWritableDatabase()");
		// TODO Auto-generated method stub
		if (isCreating && currentDB != null) {
			Log.v(logTag, "getWritableDatabase()[1]");
			return currentDB;
		}
		Log.v(logTag, "getWritableDatabase()[2]");
		return super.getWritableDatabase();
	}

	@Override
	public SQLiteDatabase getReadableDatabase() {
		Log.v(logTag, "getReadableDatabase()");
		// TODO Auto-generated method stub
		if (isCreating && currentDB != null) {
			return currentDB;
		}
		return super.getReadableDatabase();
	}

	private void generateAllDefaultData() {

		insert_nomenclature();
		insert_test_list();
		insert_test_shops();
	}

	// +++++++++++++++++++++++++++++++++ 1.1. СОЗДАНИЕ ПЕРВОНАЧАЛЬНЫХ ДАННЫХ В
	// БД++++++++++++++++++++++++++++++++++++++
	public void insert_nomenclature() {
		Log.v(logTag, "insert_nomenclature()");
		SQLiteDatabase mDb;
		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {

			mDb = getReadableDatabase();
			// System.out.println();

		}
		Log.v(logTag, "generateSomeHierarchy BEGIN");

		Log.v(logTag, "new ContentValues()");
		ContentValues initialValues = new ContentValues();
		initialValues.put("_id", -1);
		initialValues.put("parent_id", -1);
		initialValues.put("name", "Вся номенклатура");
		initialValues.put("nomenclature_code", "0000");
		initialValues.put("type", 1);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
		initialValues.clear();
		initialValues.put("_id", 0);
		initialValues.put("parent_id", -1);
		initialValues.put("name", "Молоко");
		initialValues.put("nomenclature_code", "0001");
		initialValues.put("type", 1);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
		initialValues.clear();
		initialValues.put("_id", 1);
		initialValues.put("parent_id", -1);
		initialValues.put("name", "Мясо");
		initialValues.put("nomenclature_code", "0002");
		initialValues.put("type", 2);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
		initialValues.clear();
		initialValues.put("_id", 2);
		initialValues.put("parent_id", -1);
		initialValues.put("name", "Хлеб");
		initialValues.put("nomenclature_code", "0003");
		initialValues.put("type", 3);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
		initialValues.clear();
		initialValues.put("_id", 3);
		initialValues.put("parent_id", -1);
		initialValues.put("name", "Овощи");
		initialValues.put("nomenclature_code", "0003");
		initialValues.put("type", 4);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
		initialValues.clear();
		initialValues.put("_id", 4);
		initialValues.put("parent_id", 0);
		initialValues.put("name", "Молоко 1.5");
		initialValues.put("nomenclature_code", "0004");
		initialValues.put("type", 1);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
		initialValues.clear();
		initialValues.put("_id", 5);
		initialValues.put("parent_id", 0);
		initialValues.put("name", "Молоко 2.5");
		initialValues.put("nomenclature_code", "0005");
		initialValues.put("type", 1);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
		initialValues.clear();
		initialValues.put("_id", 6);
		initialValues.put("parent_id", 0);
		initialValues.put("name", "Молоко 3.5");
		initialValues.put("nomenclature_code", "0006");
		initialValues.put("type", 1);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
		initialValues.clear();
		initialValues.put("_id", 7);
		initialValues.put("parent_id", 1);
		initialValues.put("name", "Говядина");
		initialValues.put("nomenclature_code", "0007");
		initialValues.put("type", 2);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
		initialValues.clear();
		initialValues.put("_id", 8);
		initialValues.put("parent_id", 1);
		initialValues.put("name", "Свинина");
		initialValues.put("nomenclature_code", "0008");
		initialValues.put("type", 2);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
		initialValues.clear();
		initialValues.put("_id", 9);
		initialValues.put("parent_id", 2);
		initialValues.put("name", "Хлеб пшеничный");
		initialValues.put("nomenclature_code", "0009");
		initialValues.put("type", 3);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
		initialValues.clear();
		initialValues.put("_id", 10);
		initialValues.put("parent_id", 2);
		initialValues.put("name", "Хлеб ржаной");
		initialValues.put("nomenclature_code", "0010");
		initialValues.put("type", 3);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
		initialValues.clear();
		initialValues.put("_id", 11);
		initialValues.put("parent_id", 3);
		initialValues.put("name", "Капуста");
		initialValues.put("nomenclature_code", "0011");
		initialValues.put("type", 4);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
		initialValues.clear();
		initialValues.put("_id", 12);
		initialValues.put("parent_id", 3);
		initialValues.put("name", "Морковь");
		initialValues.put("nomenclature_code", "0012");
		initialValues.put("type", 4);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
		initialValues.clear();
		initialValues.put("_id", 13);
		initialValues.put("parent_id", 3);
		initialValues.put("name", "Огурцы");
		initialValues.put("nomenclature_code", "0013");
		initialValues.put("type", 4);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
		initialValues.clear();
		initialValues.put("_id", 14);
		initialValues.put("parent_id", 3);
		initialValues.put("name", "Помидоры");
		initialValues.put("nomenclature_code", "0014");
		initialValues.put("type", 4);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
		initialValues.clear();
		initialValues.put("_id", 15);
		initialValues.put("parent_id", 3);
		initialValues.put("name", "Петрушка");
		initialValues.put("nomenclature_code", "0015");
		initialValues.put("type", 4);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
		initialValues.clear();
		initialValues.put("_id", 16);
		initialValues.put("parent_id", -1);
		initialValues.put("name", "Бакалея");
		initialValues.put("nomenclature_code", "0016");
		initialValues.put("type", 5);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
		initialValues.clear();
		initialValues.put("_id", 17);
		initialValues.put("parent_id", 16);
		initialValues.put("name", "Крупы");
		initialValues.put("nomenclature_code", "0017");
		initialValues.put("type", 5);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
		initialValues.clear();
		initialValues.put("_id", 18);
		initialValues.put("parent_id", 16);
		initialValues.put("name", "Макароны");
		initialValues.put("nomenclature_code", "0018");
		initialValues.put("type", 5);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
		initialValues.clear();
		initialValues.put("_id", 19);
		initialValues.put("parent_id", 16);
		initialValues.put("name", "Специи");
		initialValues.put("nomenclature_code", "0019");
		initialValues.put("type", 5);
		mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);

		for (int i=20;i<2500;i=i+6){
			initialValues.clear();
			initialValues.put("_id", i);
			initialValues.put("parent_id", -1);
			initialValues.put("name", "Группа товаров "+i);
			initialValues.put("nomenclature_code", "00"+i);
			initialValues.put("type", i);
			mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
			for (int j=i+1;j<i+6;j++)
			{
				initialValues.clear();
				initialValues.put("_id", j);
				initialValues.put("parent_id", i);
				initialValues.put("name", "Товар "+j);
				initialValues.put("nomenclature_code", "00"+j);
				initialValues.put("type", i);
				mDb.insert(DATABASE_TABLE_NOMENCLATURE, null, initialValues);
			}
		}
	}

	// Пока сделаю, что-бы по умолчанию при созданию БД создавались какие-то
	// списки
	public void insert_test_list() {
		Log.v(logTag, "insert_test_list()");
		SQLiteDatabase mDb;
		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {

			mDb = getReadableDatabase();
			// System.out.println();

		}
		ContentValues initialValues = new ContentValues();
		initialValues.put("_id", "0");
		initialValues.put("name", "Покупки домой");
		initialValues.put("select_flag", "0");
		Log.v(logTag, "insert_test_list() Покупки домой");
		mDb.insert(DATABASE_TABLE_LISTS, null, initialValues);
		initialValues.clear();
		// на время разработки вставляем всю номенклатуру в список покупок
		Cursor cursor = mDb.rawQuery("select * from "
				+ DATABASE_TABLE_NOMENCLATURE + " where _id>-1", null);
		if (cursor.moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				initialValues.put("list_id", "0");
				initialValues.put("item_id",
						cursor.getInt(cursor.getColumnIndex("_id")));
				initialValues.put("flag_taken", "0");
				Log.v(logTag,
						"insert_test_list() insert_item= "
								+ cursor.getInt(cursor.getColumnIndex("_id")));
				mDb.insert(DATABASE_TABLE_LISTS_ITEMS, null, initialValues);
				initialValues.clear();
				cursor.moveToNext();
			}
		}

		initialValues.put("_id", "1");
		initialValues.put("name", "Мяско");
		mDb.insert(DATABASE_TABLE_LISTS, null, initialValues);
	}

	public void insert_default_settings() {
		Log.v(logTag, "insert_default_settings()");
		SQLiteDatabase mDb;
		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {

			mDb = getReadableDatabase();
			// System.out.println();

		}
		ContentValues initialValues = new ContentValues();
		initialValues.put("main_sort", "1");
		initialValues.put("list_sort", "1");
		mDb.insert(DATABASE_TABLE_USER_SETTINGS, null, initialValues);
		initialValues.clear();
	}
	public void insert_test_shops() {
		Log.v(logTag, "insert_test_shops()");
		SQLiteDatabase mDb;
		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {

			mDb = getReadableDatabase();
			// System.out.println();

		}
		ContentValues initialValues = new ContentValues();
		initialValues.put("_id", "1");
		initialValues.put("name text", "АШАН Коммунарка");
		initialValues.put("adress", "Москва, 44 км.МКАД, пос.Сосенское д142");
		initialValues.put("map_picture", "map.png");
		initialValues.put("info", "Прекрасный магазин");
		mDb.insert(DATABASE_TABLE_SHOPS, null, initialValues);
		initialValues.clear();
	}
	
	public void insert_test_shops_items() {
		Log.v(logTag, "insert_test_shops_items()");
		SQLiteDatabase mDb;
		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {

			mDb = getReadableDatabase();
			// System.out.println();

		}
		int minvalxy=1;
		int maxvalxy=1000;
		ContentValues initialValues = new ContentValues();
		for (int i=0; i<2500;i++){
		initialValues.put("shop_id", "1");
		initialValues.put("item_id", i);
		initialValues.put("locationX", minvalxy + (int)(Math.random() * ((maxvalxy - minvalxy) + 1)));
		initialValues.put("locationY", minvalxy + (int)(Math.random() * ((maxvalxy - minvalxy) + 1)));
		mDb.insert(DATABASE_TABLE_SHOPS_ITEMS, null, initialValues);
		initialValues.clear();
		}
	}

	// +++++++++++++++++++++++2. ПОЛУЧЕНИЕ ДАННЫХ ИЗ
	// БД+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// ++++++++++++++++++++++++++2.1.Извлечение элементов
	// номенклатуры++++++++++++++++++++++++++++//
	// Извлекаем элемент номенклатуры в курсора из БД
	public Cursor fetchNomenclatureItemById(int id) throws SQLException {
		Log.v(logTag, "fetchNomenclatureItemById()");
		SQLiteDatabase mDb;
		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {
			mDb = getReadableDatabase();
			// System.out.println();
		}
		Cursor mCursor = mDb.query(true, DATABASE_TABLE_NOMENCLATURE,
				DATABASE_TABLE_NOMENCLATURE_FIELDS, "_id" + "='" + id + "'",
				null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	// Извлекаем элемент номенклатуры из курсора
	public static Item getNomenclatureItem(Cursor cursor) {
		Log.v(logTag, "getNomenclatureItem()");
		int p_id = cursor.getInt(cursor.getColumnIndex("_id"));
		String name = cursor.getString(cursor.getColumnIndex("name"));
		int parent_id = cursor.getInt(cursor.getColumnIndex("parent_id"));
		String nomenclature_code = cursor.getString(cursor
				.getColumnIndex("nomenclature_code"));
		int type = cursor.getInt(cursor.getColumnIndex("type"));

		Item item = new Item(p_id, parent_id, name, nomenclature_code, type);

		return item;
	}

	public Item getNomenclatureItemById(int id) {
		Log.v(logTag, "getNomenclatureItemById()");
		Item item = null;
		Cursor itemCursor = fetchNomenclatureItemById(id);
		if (itemCursor.moveToFirst()) {
			item = getNomenclatureItem(itemCursor);
		}
		if (itemCursor != null) {
			itemCursor.close();
		}
		return item;
	}

	// Получаем полный перечень номенклатуры из БД
	public HashMap<Integer, Item> getNomenclatureFromDB() {
		Log.v(logTag, "getNomenclatureFromDB()");
		SQLiteDatabase mDb;
		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {

			mDb = getReadableDatabase();
			// System.out.println();

		}

		HashMap<Integer, Item> result = new HashMap<Integer, Item>();
		Cursor cursor = mDb.rawQuery("select * from "
				+ DATABASE_TABLE_NOMENCLATURE + " where _id>-1", null);

		if (cursor.moveToFirst()) {

			while (cursor.isAfterLast() == false) {
				int p_id = cursor.getInt(cursor.getColumnIndex("_id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				int parent_id = cursor.getInt(cursor
						.getColumnIndex("parent_id"));
				String nomenclature_code = cursor.getString(cursor
						.getColumnIndex("nomenclature_code"));
				int type = cursor.getInt(cursor.getColumnIndex("type"));

				Item item = new Item(p_id, parent_id, name, nomenclature_code,
						type);

				// if (item.parent_id!=-1 && result.contains( parent_id ))
				// {item.addParent(result.get(parent_id));}
				result.put(item.getId(), item);
				cursor.moveToNext();
			}
		}

		for (Item i : result.values()) {
			if (i.parent_id != -1) {
				i.addParent(result.get(i.parent_id));
			}
		}

		return result;

	}

	// ++++++++++++++++++++++++++++++++++++++++2.2. Работа со списками
	// покупок++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++Получение перечня списков
	// покупок++++++++++++++++++++++++++++++++++
	public LinkedHashMap<Integer, shpmList> getSavedLists() {
		SQLiteDatabase mDb;
		Log.v(logTag, "getSavedLists()");

		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {

			mDb = getReadableDatabase();

		}

		LinkedHashMap<Integer, shpmList> result = new LinkedHashMap<Integer, shpmList>();
		Cursor cursor = mDb.rawQuery("select * from " + DATABASE_TABLE_LISTS
				+ " order by _id", null);
		if (cursor.moveToFirst()) {

			while (cursor.isAfterLast() == false) {
				int _id = cursor.getInt(cursor.getColumnIndex("_id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				int select_flag = cursor.getInt(cursor
						.getColumnIndex("select_flag"));
				shpmList list = new shpmList(_id, select_flag, name,
						mainActivity, getSavedListItems(_id));
				result.put(list.getId(), list);
				cursor.moveToNext();
			}
		}

		return result;

	}

	// ++++++++++++++++++++++++++++++++++++++++2.3. Получение элементов списка
	// покупок++++++++++++++++++++++++++++++++++
	public HashMap<Integer, ListItem> getSavedListItems(int list_id) {
		SQLiteDatabase mDb;
		Log.v(logTag, "getSavedListItems()");

		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {

			mDb = getReadableDatabase();
		}

		HashMap<Integer, ListItem> list_items = new HashMap<Integer, ListItem>();
		// list_items.add(0, null);
		Cursor cursor = mDb.rawQuery("select * from "
				+ DATABASE_TABLE_LISTS_ITEMS + " where list_id=" + list_id,
				null);
		if (cursor.moveToFirst()) {

			while (cursor.isAfterLast() == false) {
				Item item = getItemFromNomenclatureArray((int) cursor
						.getInt(cursor.getColumnIndex("item_id")));
				int taken_flag = (int) cursor.getInt(cursor
						.getColumnIndex("taken_flag"));
				Log.v(logTag, "item=" + item.getTitle());
				ListItem list_item = new ListItem(list_id, item, taken_flag, 1,
						mainActivity);// DEV 1-изменить на кол-во из БД
				/*
				 * if (item.parent_id!=-1){
				 * 
				 * list_item.addParent(list_items.get(item.parent_id)); }
				 */

				list_items.put(list_item.getId(), list_item);
				cursor.moveToNext();

			}
		}

		return list_items;

	}
	
	public int getMainSort() {
		int result = 0;
		SQLiteDatabase mDb;
		Log.v(logTag, "getSavedLists()");

		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {

			mDb = getReadableDatabase();

		}

		Cursor cursor = mDb.rawQuery("select main_sort from " + DATABASE_TABLE_USER_SETTINGS, null);
		if (cursor.moveToFirst()) {

			if (cursor.isAfterLast() == false) {
                      result = cursor.getInt(cursor
						.getColumnIndex("main_sort"));

			}
		}

		return result;

	}
	public int getListSort() {
		int result = 0;
		SQLiteDatabase mDb;
		Log.v(logTag, "getSavedLists()");

		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {

			mDb = getReadableDatabase();

		}

		Cursor cursor = mDb.rawQuery("select list_sort from " + DATABASE_TABLE_USER_SETTINGS, null);
		if (cursor.moveToFirst()) {

			if (cursor.isAfterLast() == false) {
                      result = cursor.getInt(cursor
						.getColumnIndex("list_sort"));

			}
		}

		return result;

	}

	// +++++++++++++++++++++++3.СОХРАНЕНИЕ ДАННЫХ В
	// БД+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// ++++++++++++++++++++++++++++++++++++++++3.1. Сохранение списка
	// покупок++++++++++++++++++++++++++++++++++
	public long saveNewListDB(String name) {
		SQLiteDatabase mDb;
		Log.v(logTag, "saveNewListDB()");

		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {

			mDb = getReadableDatabase();
		}
		ContentValues newValues = new ContentValues();
		// Задайте значения для каждой строки.
		newValues.put("name", name);

		// Вставляем строку в базу данных.

		long result = mDb.insert(DATABASE_TABLE_LISTS, null, newValues);
		lists = getSavedLists();
		return result;
	}

	// ++++++++++++++++++++++++++++++++++++++++3.3. Сохранение элементов
	// списка++++++++++++++++++++++++++++++++++
	public long saveNewListItemDB(shpmList list, ListItem listItem) {
		SQLiteDatabase mDb;
		Log.v(logTag, "saveNewListItemDB()");

		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {

			mDb = getReadableDatabase();
		}
		ContentValues newValues = new ContentValues();
		// Задайте значения для каждой строки.
		newValues.put("list_id", list.getId());
		newValues.put("item_id", listItem.item.getId());
		return mDb.insert(DATABASE_TABLE_LISTS_ITEMS, null, newValues);
	}

	public long saveNewListItemDB(shpmList list,
			ru.r8digitaltech7.shoppingmap.Item item) {
		SQLiteDatabase mDb;
		Log.v(logTag, "saveNewListItemDB()");

		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {

			mDb = getReadableDatabase();
		}
		ContentValues newValues = new ContentValues();
		// Задайте значения для каждой строки.
		newValues.put("list_id", list.getId());
		newValues.put("item_id", item.getId());
		return mDb.insert(DATABASE_TABLE_LISTS_ITEMS, null, newValues);
	}

	public long saveNewListItemDB(shpmList list, int p_item_id) {
		SQLiteDatabase mDb;
		Log.v(logTag, "saveNewListItemDB()");

		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {

			mDb = getReadableDatabase();
		}
		ContentValues newValues = new ContentValues();
		// Задайте значения для каждой строки.
		newValues.put("list_id", list.getId());
		newValues.put("item_id", p_item_id);
		return mDb.insert(DATABASE_TABLE_LISTS_ITEMS, null, newValues);
	}

	// +++++++++++++++++++++++4.ИЗМЕНЕНИЕ ДАННЫХ В
	// БД+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public long updateListNameDB(int listId, String p_newName) {
		SQLiteDatabase mDb;
		Log.v(logTag, "updateListNameDB()");

		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {

			mDb = getReadableDatabase();
		}
		lists.get(listId).setName(p_newName);
		ContentValues newValues = new ContentValues();

		newValues.put("name", p_newName);

		return mDb.update(DATABASE_TABLE_LISTS, newValues,
				"_id" + "=" + listId, null);
	}

	public void setListSelectedFlag(int listId, Integer p_select_flag) {
		SQLiteDatabase mDb;
		Log.v(logTag, "setListSelectedFlag()");

		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {

			mDb = getReadableDatabase();
		}
		// lists.get(listId).set_selected(p_select_flag);
		ContentValues newValues = new ContentValues();
		newValues.put("select_flag", p_select_flag);
		mDb.update(DATABASE_TABLE_LISTS, newValues, "_id" + "=" + listId, null);

	}

	public void setListItemTakenFlag(int listId, int itemId,
			Integer p_select_flag) {
		SQLiteDatabase mDb;
		Log.v(logTag, "setListItemTakenFlag("+p_select_flag+")");

		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {

			mDb = getReadableDatabase();
		}
		// lists.get(listId).set_selected(p_select_flag);
		ContentValues newValues = new ContentValues();
		newValues.put("taken_flag", p_select_flag);
		mDb.update(DATABASE_TABLE_LISTS_ITEMS, newValues, "list_id" + "="
				+ listId + " and item_id=" + itemId, null);

	}

	public void saveListSort(int p_sort_type) {
		SQLiteDatabase mDb;
		Log.v(logTag, "setListSelectedFlag()");

		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {
			mDb = getReadableDatabase();
		}
		ContentValues newValues = new ContentValues();
		newValues.put("list_sort", p_sort_type);
		mDb.update(DATABASE_TABLE_USER_SETTINGS, newValues, null, null);
	}

	public void saveMainSort(int p_sort_type) {
		SQLiteDatabase mDb;
		Log.v(logTag, "setListSelectedFlag()");

		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {
			mDb = getReadableDatabase();
		}
		ContentValues newValues = new ContentValues();
		newValues.put("main_sort", p_sort_type);
		mDb.update(DATABASE_TABLE_USER_SETTINGS, newValues, null, null);
	}

	// +++++++++++++++++++++++5.УДАЛЕНИЕ ДАННЫХ ИЗ
	// БД+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// ++++++++++++++++++++++++++++++++++++++++5.1. Удаление списка
	// покупок++++++++++++++++++++++++++++++++++
	public long deleteListDB(int list_id) {
		SQLiteDatabase mDb;
		Log.v(logTag, "deleteListDB(" + list_id + ")");

		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {

			mDb = getReadableDatabase();
		}
		long result = mDb.delete(DATABASE_TABLE_LISTS, "_id" + "=" + list_id,
				null);
		lists.remove(list_id);
		return result;
	}

	// ++++++++++++++++++++++++++++++++++++++++5.2. Удаление элемента
	// списка++++++++++++++++++++++++++++++++++
	public long deleteListItemDB(int p_list_id, int p_item_id) {
		SQLiteDatabase mDb;
		Log.v(logTag, "deleteListItemDB(" + p_item_id + ")");

		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {

			mDb = getReadableDatabase();
		}
		return mDb.delete(DATABASE_TABLE_LISTS_ITEMS, "item_id" + "="
				+ p_item_id + " and list_id=" + p_list_id, null);
	}

}