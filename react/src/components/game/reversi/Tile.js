function Tile({tile, onPlaceStone}) {

    let tileClass;
    if('FREE'.localeCompare(tile?.tileState) === 0) {
        tileClass = "tile"

    } else {
        tileClass = "stone-" + tile?.stone.player.color.toLowerCase();
    }

    const handleTileClicked = () => {
        onPlaceStone();
    }

    const handleRightClick = event => {
        event.preventDefault();
    }

    return (
        <td className="tile" onClick={handleTileClicked} onContextMenu={handleRightClick}>
            <div>
                <div className={tileClass + "-circle"}></div>
            </div>
        </td>
    )
}

export default Tile;