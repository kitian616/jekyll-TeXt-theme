import pandas as pd
import json

with open("quotes.json", "r") as f:
    quotes = json.load(f)
    df = pd.DataFrame(quotes)

new_df = df[df.duplicated(["Quote"]) == False].reset_index(drop=True)

with open("quotes_corrected.json","w") as f:
    new_df.to_json(f,orient="records")
