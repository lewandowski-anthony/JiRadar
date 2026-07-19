import React, { createContext, useContext, useState, type ReactElement } from 'react';

const TabsContext = createContext<{
    activeTab: string;
    setActiveTab: (id: string) => void;
} | null>(null);

export function Tab({ children }: Readonly<{ children: React.ReactNode; [key: string]: any }>) {
    const context = useContext(TabsContext);
    if (!context) throw new Error('<Tab /> Must be used in <Tabs />');

    return <>{children}</>;
}

interface TabsProps {
    children: React.ReactNode;
    defaultActiveId?: string;
}

export function Tabs({ children, defaultActiveId }: TabsProps) {
    const validChildren = React.Children.toArray(children).filter(
        (child): child is ReactElement<{ id: string; label: string; icon?: string; children: React.ReactNode }> =>
            React.isValidElement(child)
    );

    const [activeTab, setActiveTab] = useState(
        defaultActiveId || validChildren[0]?.props.id || ''
    );

    const contextValue = React.useMemo(() => ({
        activeTab,
        setActiveTab
    }), [activeTab]);

    return (
        <TabsContext.Provider value={contextValue}>
            <div className="space-y-6 w-full">
                <div className="flex border-b border-slate-800 w-full overflow-x-auto scrollbar-none scroll-smooth snap-x snap-mandatory [-webkit-overflow-scrolling:touch]">
                    {validChildren.map((child) => {
                        const { id, label, icon } = child.props;
                        const isActive = activeTab === id;

                        return (
                            <button
                                key={id}
                                type="button"
                                onClick={() => setActiveTab(id)}
                                className={`px-6 py-3 text-sm font-medium border-b-2 whitespace-nowrap shrink-0 snap-start transition-colors duration-200 ${
                                    isActive
                                        ? 'border-indigo-500 text-indigo-400'
                                        : 'border-transparent text-slate-400 hover:text-slate-200'
                                }`}
                            >
                                {icon && <span className={`${icon} mr-2`} />}
                                {label}
                            </button>
                        );
                    })}
                </div>

                <div className="w-full">
                    {validChildren.find((child) => child.props.id === activeTab)?.props.children}
                </div>
            </div>
        </TabsContext.Provider>
    );
}