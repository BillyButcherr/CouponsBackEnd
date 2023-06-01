import "./ContentContainer.css";

function ContentContainer({ children } : any) : JSX.Element {
    return (
        <div className="content-container">
			{children}
        </div>
    );
}

export default ContentContainer;
