package com.adut.pasar.app.feature.product;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

public class ProductSuggestion implements SearchSuggestion {

    private String title;

    public ProductSuggestion(String title) {
        this.title = title.toLowerCase();
    }

    public ProductSuggestion(Parcel source) {
        this.title = source.readString();
    }

    @Override
    public String getBody() {
        return title;
    }

    public static final Creator<ProductSuggestion> CREATOR = new Creator<ProductSuggestion>() {
        @Override
        public ProductSuggestion createFromParcel(Parcel in) {
            return new ProductSuggestion(in);
        }

        @Override
        public ProductSuggestion[] newArray(int size) {
            return new ProductSuggestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }
}
