import Tile from "./Tile";

function Field({tiles, onPlaceStone}) {
    return (
        <table className="reversifield">
            <tbody>
                {tiles.map((row, rowIndex) => (
                    <tr key={`row-${rowIndex}`}>
                        {row.map((tile, colIndex) => (
                            <Tile key={`tile-${rowIndex}-${colIndex}`}
                                  tile={tile}
                                  onPlaceStone={() => onPlaceStone(rowIndex, colIndex)}/>
                        ))}
                    </tr>
                ))}
            </tbody>
        </table>
    )
}

export default Field;