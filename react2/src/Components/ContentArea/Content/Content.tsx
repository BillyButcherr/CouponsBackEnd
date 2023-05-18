import "./Content.css";

function Content(props : any): JSX.Element {
    return (
        <div className="content">
            <h3>{props.title}</h3>
			{props.children}
        </div>
    );
}

export default Content;
