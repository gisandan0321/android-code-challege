package com.daywalker.codechallenge.models;

public class Track {

    private int trackId;
    private String trackName;
    private String wrapperType;
    private String kind;
    private int collectionId;
    private String artistName;
    private String collectionName;
    private String collectionCensoredName;
    private String trackCensoredName;
    private int collectionArtistId;
    private String collectionArtistViewUrl;
    private String collectionViewUrl;
    private String trackViewUrl;
    private String previewUrl;
    private String artworkUrl30;
    private String artworkUrl60;
    private String artworkUrl100;
    private float collectionPrice;
    private float trackPrice;
    private float trackRentalPrice;
    private float collectionHdPrice;
    private float trackHdPrice;
    private float trackHdRentalPrice;
    private String releaseDate;
    private String collectionExplicitness;
    private String trackExplicitness;
    private int discCount;
    private int discNumber;
    private int trackCount;
    private int trackNumber;
    private int trackTimeMillis;
    private String country;
    private String currency;
    private String primaryGenreName;
    private String contentAdvisoryRating;
    private String shortDescription;
    private String longDescription;
    private boolean hasITunesExtras;

    /**
     * Track Model Constructor
     * @param trackId int
     */
    public Track(int trackId) {
        this.trackId = trackId;
    }

    /**
     * Set Name
     * @param trackName String
     * @return Track
     */
    public Track setTrackName(String trackName) {
        this.trackName = trackName;
        return this;
    }

    /**
     * Set Wrapper Type
     * @param wrapperType String
     */
    public Track setWrapperType(String wrapperType) {
        this.wrapperType = wrapperType;
        return this;
    }

    /**
     * Set Kind
     * @param kind String
     * @return this
     */
    public Track setKind(String kind) {
        this.kind = kind;
        return this;
    }

    /**
     * Set Collection Id
     * @param collectionId int
     * @return this
     */
    public Track setCollectionId(int collectionId) {
        this.collectionId = collectionId;
        return this;
    }

    /**
     * Set Artist Name
     * @param artistName String
     * @return int
     */
    public Track setArtistName(String artistName) {
        this.artistName = artistName;
        return this;
    }

    /**
     * Set Collection Name
     * @param collectionName String
     * @return this
     */
    public Track setCollectionName(String collectionName) {
        this.collectionName = collectionName;
        return this;
    }

    /**
     * Set Collection Censored Name
     * @param collectionCensoredName String
     * @return this
     */
    public Track setCollectionCensoredName(String collectionCensoredName) {
        this.collectionCensoredName = collectionCensoredName;
        return this;
    }

    /**
     * Set Censored Name
     * @param trackCensoredName String
     * @return this
     */
    public Track setTrackCensoredName(String trackCensoredName) {
        this.trackCensoredName = trackCensoredName;
        return this;
    }

    /**
     * Set Collection Artist ID
     * @param collectionArtistId int
     * @return this
     */
    public Track setCollectionArtistId(int collectionArtistId) {
        this.collectionArtistId = collectionArtistId;
        return this;
    }

    /**
     * Set Collect Artist View URL
     * @param collectionArtistViewUrl String
     * @return this
     */
    public Track setCollectionArtistViewUrl(String collectionArtistViewUrl) {
        this.collectionArtistViewUrl = collectionArtistViewUrl;
        return this;
    }

    /**
     * Set Collection View URL
     * @param collectionViewUrl String
     * @return this
     */
    public Track setCollectionViewUrl(String collectionViewUrl) {
        this.collectionViewUrl = collectionViewUrl;
        return this;
    }

    /**
     * Set Track View URL
     * @param trackViewUrl String
     * @return this
     */
    public Track setTrackViewUrl(String trackViewUrl) {
        this.trackViewUrl = trackViewUrl;
        return this;
    }

    /**
     * Set Preview URL
     * @param previewUrl String
     * @return this
     */
    public Track setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
        return this;
    }

    /**
     * Set Artwork URL 30
     * @param artworkUrl30 String
     * @return this
     */
    public Track setArtworkUrl30(String artworkUrl30) {
        this.artworkUrl30 = artworkUrl30;
        return this;
    }

    /**
     * Set Artwork URL 60
     * @param artworkUrl60 String
     * @return this
     */
    public Track setArtworkUrl60(String artworkUrl60) {
        this.artworkUrl60 = artworkUrl60;
        return this;
    }

    /**
     * Set Artwork URL 100
     * @param artworkUrl100 String
     * @return this
     */
    public Track setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
        return this;
    }

    /**
     * Set Collection Price
     * @param collectionPrice float
     * @return this
     */
    public Track setCollectionPrice(float collectionPrice) {
        this.collectionPrice = collectionPrice;
        return this;
    }

    /**
     * Set Track Price
     * @param trackPrice float
     * @return this
     */
    public Track setTrackPrice(float trackPrice) {
        this.trackPrice = trackPrice;
        return this;
    }

    /**
     * Set Track Rental Price
     * @param trackRentalPrice float
     * @return this
     */
    public Track setTrackRentalPrice(float trackRentalPrice) {
        this.trackRentalPrice = trackRentalPrice;
        return this;
    }

    /**
     * Set Collection HD PRice
     * @param collectionHdPrice float
     * @return this
     */
    public Track setCollectionHdPrice(float collectionHdPrice) {
        this.collectionHdPrice = collectionHdPrice;
        return this;
    }

    /**
     * Set Track HD Price
     * @param trackHdPrice float
     * @return this
     */
    public Track setTrackHdPrice(float trackHdPrice) {
        this.trackHdPrice = trackHdPrice;
        return this;
    }

    /**
     * Set Track HD Rental Price
     * @param trackHdRentalPrice String
     * @return this
     */
    public Track setTrackHdRentalPrice(float trackHdRentalPrice) {
        this.trackHdRentalPrice = trackHdRentalPrice;
        return this;
    }

    /**
     * Set Release Date
     * @param releaseDate String
     * @return this
     */
    public Track setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    /**
     * Set Collection Explicitness
     * @param collectionExplicitness String
     * @return this
     */
    public Track setCollectionExplicitness(String collectionExplicitness) {
        this.collectionExplicitness = collectionExplicitness;
        return this;
    }

    /**
     * Set Track Expicitness
     * @param trackExplicitness float
     * @return this
     */
    public Track setTrackExplicitness(String trackExplicitness) {
        this.trackExplicitness = trackExplicitness;
        return this;
    }

    /**
     * Set Disc Count
     * @param discCount int
     * @return this
     */
    public Track setDiscCount(int discCount) {
        this.discCount = discCount;
        return this;
    }

    /**
     * Set Disc Number
     * @param discNumber int
     * @return this;
     */
    public Track setDiscNumber(int discNumber) {
        this.discNumber = discNumber;
        return this;
    }

    /**
     * Set Track Count
     * @param trackCount int
     * @return this
     */
    public Track setTrackCount(int trackCount) {
        this.trackCount = trackCount;
        return this;
    }

    /**
     * Set Track Number
     * @param trackNumber int
     * @return this
     */
    public Track setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
        return this;
    }

    /**
     * Set Track Time Milis
     * @param trackTimeMillis int
     * @return this
     */
    public Track setTrackTimeMillis(int trackTimeMillis) {
        this.trackTimeMillis = trackTimeMillis;
        return this;
    }

    /**
     * Set Country
     * @param country String
     * @return this
     */
    public Track setCountry(String country) {
        this.country = country;
        return this;
    }

    /**
     * Set Currency
     * @param currency String
     * @return this
     */
    public Track setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    /**
     * Set Primary Genre Name
     * @param primaryGenreName String
     * @return this
     */
    public Track setPrimaryGenreName(String primaryGenreName) {
        this.primaryGenreName = primaryGenreName;
        return this;
    }

    /**
     * Set Content Advisory Rating
     * @param contentAdvisoryRating String
     * @return int
     */
    public Track setContentAdvisoryRating(String contentAdvisoryRating) {
        this.contentAdvisoryRating = contentAdvisoryRating;
        return this;
    }

    /**
     * Set Short Description
     * @param shortDescription String
     * @return this
     */
    public Track setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    /**
     * Set Long Description
     * @param longDescription String
     * @return this
     */
    public Track setLongDescription(String longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    /**
     * Set Has ITunes Extras
     * @param hasITunesExtras boolean
     * @return this
     */
    public Track setHasITunesExtras(boolean hasITunesExtras) {
        this.hasITunesExtras = hasITunesExtras;
        return this;
    }

    /**
     * Get Track ID
     * @return int
     */
    public int getTrackId() {
        return trackId;
    }

    /**
     * Get Track Name
     * @return string
     */
    public String getTrackName() {
        return trackName;
    }

    /**
     * Get Wrapper Type
     * @return String
     */
    public String getWrapperType() {
        return wrapperType;
    }

    /**
     * Get Kind
     * @return String
     */
    public String getKind() {
        return kind;
    }

    /**
     * Get Collection Id
     * @return int
     */
    public int getCollectionId() {
        return collectionId;
    }

    /**
     * Get Artist Name
     * @return String
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * Get Collection Name
     * @return String
     */
    public String getCollectionName() {
        return collectionName;
    }

    /**
     * Get Collection Censored Name
     * @return String
     */
    public String getCollectionCensoredName() {
        return collectionCensoredName;
    }

    /**
     * Get Track Censored Name
     * @return String
     */
    public String getTrackCensoredName() {
        return trackCensoredName;
    }

    /**
     * Get Collection Artist Id
     * @return String
     */
    public int getCollectionArtistId() {
        return collectionArtistId;
    }

    /**
     * Get Collection Artist View URL
     * @return String
     */
    public String getCollectionArtistViewUrl() {
        return collectionArtistViewUrl;
    }

    /**
     * Get Collection View URL
     * @return String
     */
    public String getCollectionViewUrl() {
        return collectionViewUrl;
    }

    /**
     * Get Track View URL
     * @return this
     */
    public String getTrackViewUrl() {
        return trackViewUrl;
    }

    /**
     * Get Preview URL
     * @return String
     */
    public String getPreviewUrl() {
        return previewUrl;
    }

    /**
     * Get Artwork URL 30
     * @return String
     */
    public String getArtworkUrl30() {
        return artworkUrl30;
    }

    /**
     * Get Artwork URL 60
     * @return String
     */
    public String getArtworkUrl60() {
        return artworkUrl60;
    }

    /**
     * Get Artwork URL 100
     * @return String
     */
    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    /**
     * Get Collection Price
     * @return float
     */
    public float getCollectionPrice() {
        return collectionPrice;
    }

    /**
     * Get Track Price
     * @return float
     */
    public float getTrackPrice() {
        return trackPrice;
    }

    /**
     * Get Track Rental Price
     * @return float
     */
    public float getTrackRentalPrice() {
        return trackRentalPrice;
    }

    /**
     * Get Collection HD Price
     * @return float
     */
    public float getCollectionHdPrice() {
        return collectionHdPrice;
    }

    /**
     * Get Track HD Price
     * @return float
     */
    public float getTrackHdPrice() {
        return trackHdPrice;
    }

    /**
     * Get Track HD Rental Price
     * @return float
     */
    public float getTrackHdRentalPrice() {
        return trackHdRentalPrice;
    }

    /**
     * Get Release Date
     * @return String
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Get Collection Explicitness
     * @return String
     */
    public String getCollectionExplicitness() {
        return collectionExplicitness;
    }

    /**
     * Get Track Explicitness
     * @return String
     */
    public String getTrackExplicitness() {
        return trackExplicitness;
    }

    /**
     * Get Disc Count
     * @return int
     */
    public int getDiscCount() {
        return discCount;
    }

    /**
     * Get Disc Number
     * @return int
     */
    public int getDiscNumber() {
        return discNumber;
    }

    /**
     * Get Track Count
     * @return int
     */
    public int getTrackCount() {
        return trackCount;
    }

    /**
     * Get Track Number
     * @return int
     */
    public int getTrackNumber() {
        return trackNumber;
    }

    /**
     * Get Track Time Milis
     * @return int
     */
    public int getTrackTimeMillis() {
        return trackTimeMillis;
    }

    /**
     * Get Country
     * @return String
     */
    public String getCountry() {
        return country;
    }

    /**
     * Get Currency
     * @return String
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Get Primary Genre Name
     * @return String
     */
    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    /**
     * Get Content Advisory Rating
     * @return String
     */
    public String getContentAdvisoryRating() {
        return contentAdvisoryRating;
    }

    /**
     * Get Short Description
     * @return String
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Get Long Description
     * @return String
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * Has ITunes Extra
     * @return boolean
     */
    public boolean hasItunes() {
        return hasITunesExtras;
    }
}
