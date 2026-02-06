@echo off
set /p cat="What category of the card?"
set /p file="What file name of the card?"

set /p card_id="What is the card ID?"
set /p card_name="What is the name of the card?"
set /p card_type="What is the card type? (Board, Trap, Action)"
set /p card_subtype="What is the card subtype? (Trap, Action, Hauler, Anchor, Rope, Heavy, Dredger, Winch)"
set /p card_set="What is the card set?"
set /p card_rarity="What is the card rarity? (Frayed, Braided, Reinforced, Galvanized, Unbreakable)"

set /p card_grip="What is the card grip power?"
set /p card_pull="What is the card pull power?"
set /p card_rally="What is the card rally cost?"

set /p card_desc="What is the card desc?"
set /p card_flavor="What is the card flavor text?"

echo Creating file...
type nul > .\database\cards\%cat%\%file%.crd

echo { >> .\database\cards\%cat%\%file%.crd
echo   "header": { >> .\database\cards\%cat%\%file%.crd
echo     "id": "%card_id%", >> .\database\cards\%cat%\%file%.crd
echo     "name": "%card_name%", >> .\database\cards\%cat%\%file%.crd
echo     "type": "%card_type%", >> .\database\cards\%cat%\%file%.crd
echo     "sub_type": "%card_subtype%", >> .\database\cards\%cat%\%file%.crd
echo     "set": "%card_set%", >> .\database\cards\%cat%\%file%.crd
echo     "rarity": "%card_rarity%" >> .\database\cards\%cat%\%file%.crd
echo   }, >> .\database\cards\%cat%\%file%.crd
echo   "stats": { >> .\database\cards\%cat%\%file%.crd
echo     "grip_power": %card_grip%, >> .\database\cards\%cat%\%file%.crd
echo     "pull_power": %card_pull%, >> .\database\cards\%cat%\%file%.crd
echo     "rally_cost": %card_rally% >> .\database\cards\%cat%\%file%.crd
echo   }, >> .\database\cards\%cat%\%file%.crd
echo   "flavor": { >> .\database\cards\%cat%\%file%.crd
echo     "desc": "%card_desc%", >> .\database\cards\%cat%\%file%.crd
echo     "text": "%card_flavor%", >> .\database\cards\%cat%\%file%.crd
echo     "art_asset:": "path/to/art" >> .\database\cards\%cat%\%file%.crd
echo   } >> .\database\cards\%cat%\%file%.crd
echo } >> .\database\cards\%cat%\%file%.crd
pause