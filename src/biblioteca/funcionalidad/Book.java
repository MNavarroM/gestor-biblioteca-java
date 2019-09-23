package biblioteca.funcionalidad;

import java.sql.Date;

import biblioteca.funcionalidad.excepciones.PriceException;
import biblioteca.funcionalidad.excepciones.TituloVacioException;

public class Book {

	String title;
	String urlCover=null;
	String isbn=null;
	String genre=null;
	String subgenre=null;
	String author=null;
	String editorial=null;
	String location=null;
	String language=null;
	String edition=null;
	Integer puntuation=null;
	String read=null;
	String bookBinding=null;
	Integer yearPublication=null;
	String comment=null;
	String collection=null;
	Double price=null;
	String shopPlace=null;
	java.sql.Date dateBuy=null;
	
	public Book(String title, String urlCover, String isbn, String genre, String subgenre, String author,
			String editorial, String location, String language, String edition, Integer puntuation, String read,
			String bookBinding, Integer yearPublication, String comment, String collection, Double price,
			String shopPlace, Date dateBuy) throws TituloVacioException, PriceException {
		super();
		setTitle(title);
		setUrlCover(urlCover);
		setIsbn(isbn);
		setGenre(genre);
		setSubgenre(subgenre);
		setAuthor(author);
		setEditorial(editorial);
		setLocation(location);
		setLanguage(language);
		setEdition(edition);
		setPuntuation(puntuation);
		setRead(read);
		setBookBinding(bookBinding);
		setYearPublication(yearPublication);
		setComment(comment);
		setCollection(collection);
		setPrice(price);
		setShopPlace(shopPlace);
		setDateBuy(dateBuy);;
	}

	String getTitle() {
		return title;
	}

	void setTitle(String title) throws TituloVacioException {
		if(title.equals(""))
			throw new TituloVacioException("Debes rellenar el título del libro");
		this.title = title;
	}

	String getUrlCover() {
		return urlCover;
	}

	void setUrlCover(String urlCover) {
		this.urlCover = urlCover;
	}

	String getIsbn() {
		return isbn;
	}

	void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	String getGenre() {
		return genre;
	}

	void setGenre(String genre) {
		this.genre = genre;
	}

	String getSubgenre() {
		return subgenre;
	}

	void setSubgenre(String subgenre) {
		this.subgenre = subgenre;
	}

	String getAuthor() {
		return author;
	}

	void setAuthor(String author) {
		this.author = author;
	}

	String getEditorial() {
		return editorial;
	}

	void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	String getLocation() {
		return location;
	}

	void setLocation(String location) {
		this.location = location;
	}

	String getLanguage() {
		return language;
	}

	void setLanguage(String language) {
		this.language = language;
	}

	String getEdition() {
		return edition;
	}

	void setEdition(String edition) {
		this.edition = edition;
	}

	Integer getPuntuation() {
		return puntuation;
	}

	void setPuntuation(Integer puntuation) {
		this.puntuation = puntuation;
	}

	String getRead() {
		return read;
	}

	void setRead(String read) {
		this.read = read;
	}

	String getBookBinding() {
		return bookBinding;
	}

	void setBookBinding(String bookBinding) {
		this.bookBinding = bookBinding;
	}

	Integer getYearPublication() {
		return yearPublication;
	}

	void setYearPublication(Integer yearPublication) {
		this.yearPublication = yearPublication;
	}

	String getComment() {
		return comment;
	}

	void setComment(String comment) {
		this.comment = comment;
	}

	String getCollection() {
		return collection;
	}

	void setCollection(String collection) {
		this.collection = collection;
	}

	Double getPrice() {
		return price;
	}

	void setPrice(Double price) throws PriceException {
		if(price!=null && price<0)
			throw new PriceException("El precio no puede ser negativo");
		this.price = price;
	}

	String getShopPlace() {
		return shopPlace;
	}

	void setShopPlace(String shopPlace) {
		this.shopPlace = shopPlace;
	}

	java.sql.Date getDateBuy() {
		return dateBuy;
	}

	void setDateBuy(java.sql.Date dateBuy) {
		this.dateBuy = dateBuy;
	}

	
	

}
